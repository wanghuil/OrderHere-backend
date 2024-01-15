resource "kubernetes_namespace" "orderhere" {
  metadata {
    name = "orderhere-${var.PREFIX}"
  }
  depends_on = [aws_eks_cluster.dev]
}
resource "kubernetes_deployment" "orderhere" {
  metadata {
    name      = "orderhere-${var.PREFIX}"
    namespace = kubernetes_namespace.orderhere.metadata[0].name
  }

  spec {
    replicas = 2

    selector {
      match_labels = {
        app = "orderhere-${var.PREFIX}"
      }
    }

    template {
      metadata {
        labels = {
          app = "orderhere-${var.PREFIX}"
        }
      }

      spec {
        container {
          image = var.ECR_IMAGE
          name  = "orderhere-backend-container"

          env {
            name  = "POSTGRES_URL"
            value = var.POSTGRES_URL
          }

          env {
            name  = "POSTGRES_USERNAME"
            value = var.POSTGRES_USERNAME
          }

          env {
            name  = "POSTGRES_PASSWORD"
            value = var.POSTGRES_PASSWORD
          }

          port {
            container_port = 8080
          }
        }
      }
    }
  }
}
resource "kubernetes_service" "orderhere" {
  metadata {
    name      = "orderhere-${var.PREFIX}"
    namespace = kubernetes_namespace.orderhere.metadata[0].name
  }

  spec {
    selector = {
      app = "orderhere-${var.PREFIX}"
    }

    port {
      port        = 80
      target_port = 8080
      protocol    = "TCP"
    }

    type = "ClusterIP"
  }
}
resource "kubernetes_ingress_v1" "orderhere" {
  metadata {
    name      = "orderhere-${var.PREFIX}"
    namespace = kubernetes_namespace.orderhere.metadata[0].name
    annotations = {
      "alb.ingress.kubernetes.io/scheme"               = "internet-facing"
      "alb.ingress.kubernetes.io/target-type"          = "ip"
      "alb.ingress.kubernetes.io/certificate-arn"      = module.cert.arn
      "alb.ingress.kubernetes.io/listen-ports"         = jsonencode([{"HTTP": 80}, {"HTTPS": 443}])
      "alb.ingress.kubernetes.io/ssl-redirect"         = "443"
      "alb.ingress.kubernetes.io/group.name"           = "orderhere-${var.PREFIX}"
    }
  }

  spec {
    ingress_class_name = "alb"
    rule {
      host = var.r53_record_name
      http {
        path {
          path     = "/"
          path_type = "Prefix"

          backend {
            service {
              name = "orderhere-${var.PREFIX}"
              port {
                number = 80
              }
            }
          }
        }
      }
    }
  }
}

provider "kubernetes" {
  config_path = "~/.kube/config"
}

#add domain A record
#do it manually! 
resource "aws_route53_record" "alb_record" {
  zone_id = var.r53_zone_id
  name    = var.r53_record_name
  type    = "A"

  alias {
    name                   = var.alb_dns_name #check dns name once alb created
    zone_id                = var.alb_zone_id #check zone id once alb created
    evaluate_target_health = true
  }
}
