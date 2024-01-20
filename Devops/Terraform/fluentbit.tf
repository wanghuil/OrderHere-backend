#This role is different from the EKS cluster role (aws_iam_role.dev)
resource "aws_iam_policy" "fluent_bit_cloudwatch" {
  policy = jsonencode(
    {
      Statement = [
        {
          Action = [
            "cloudwatch:PutMetricData",
            "logs:CreateLogGroup",
            "logs:CreateLogStream",
            "logs:DescribeLogGroups",
            "logs:DescribeLogStreams",
            "logs:PutLogEvents",
            "logs:PutRetentionPolicy",
          ]
          Effect   = "Allow"
          Resource = "*"
          Sid      = "VisualEditor0"
        },
      ]
      Version = "2012-10-17"
    }
  )
}

resource "aws_iam_role" "fluent_bit_role" {
  name        = "fluentbit"
  description = "IAM role used by fluentbit inside EKS clusters"
  assume_role_policy = jsonencode(
    {
      Statement = [
        {
          Action = "sts:AssumeRoleWithWebIdentity"
          Condition = {
            StringEquals = {
              # Add the sts.amazonaws.com to the audience
              "${replace(data.aws_eks_cluster.dev.identity[0].oidc[0].issuer, "https://", "")}:aud" = ["system:serviceaccount:kube-system:fluentbit", "sts.amazonaws.com"]
            }
          }
          Effect = "Allow",
          Principal = {
            Federated = "arn:aws:iam::${data.aws_caller_identity.dev.account_id}:oidc-provider/${replace(data.aws_eks_cluster.dev.identity[0].oidc[0].issuer, "https://", "")}"
          }
        },
      ]
      Version = "2012-10-17"
    }
  )
}

resource "aws_iam_role_policy_attachment" "fluent_bit_attach" {
  policy_arn = aws_iam_policy.fluent_bit_cloudwatch.arn
  role       = aws_iam_role.fluent_bit_role.name
}

#use cluster role to read pod logs from other namespace, e.g. orderhere-dev
resource "kubernetes_cluster_role" "fluent_bit_read" {
  metadata {
    name = "fluent-bit-read"
  }

  rule {
    api_groups = [""]
    resources  = ["pods", "namespaces"]
    verbs      = ["get", "list", "watch"]
  }

  rule {
    api_groups = [""]
    resources  = ["pods/log"]
    verbs      = ["get", "list", "watch"]
  }
}

resource "kubernetes_cluster_role_binding" "fluent_bit_read" {
  metadata {
    name = "fluent-bit-read"
  }

  subject {
    kind      = "ServiceAccount"
    name      = "fluentbit" # Directly use the name of the existing service account
    namespace = "kube-system"
  }

  role_ref {
    kind      = "ClusterRole"
    name      = kubernetes_cluster_role.fluent_bit_read.metadata[0].name
    api_group = "rbac.authorization.k8s.io"
  }
}


resource "helm_release" "fluentbit" {
  count      = var.enable_fluentbit ? 1 : 0
  name       = "fluentbit"
  namespace  = "kube-system"
  repository = "https://aws.github.io/eks-charts"
  chart      = "aws-for-fluent-bit"
  version    = "0.1.32"
  timeout    = 600

  values = [
    templatefile(
      "fluent-bit.yaml",
      {
        region                = var.region
        iam_role_arn          = aws_iam_role.fluent_bit_role.arn
        cluster_name          = var.cluster_name
        log_retention_in_days = var.log_retention_in_days
      }
    )
  ]
  # depends_on = [aws_iam_role_policy_attachment.fluent_bit_attach, aws_iam_role.fluent_bit_role, kubernetes_cluster_role_binding.fluent_bit_read]
}
#TBC, cannot read from orderhere-dev namespace???

