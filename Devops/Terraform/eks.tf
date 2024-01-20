resource "aws_iam_role" "dev" {
  name               = "eks-cluster-${var.cluster_name}"
  assume_role_policy = <<POLICY
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Principal": {
        "Service": "eks.amazonaws.com"
      },
      "Action": "sts:AssumeRole"
    }
  ]
}
POLICY
}

# allow eks control plane logging
resource "aws_iam_policy" "eks_control_plane_logs" {
  name   = "eks-control-plane-logs-policy"
  policy = <<POLICY
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "logs:CreateLogGroup",
        "logs:CreateLogStream",
        "logs:PutLogEvents"
      ],
      "Resource": "arn:aws:logs:*:*:*"
    }
  ]
}
POLICY
}

resource "aws_iam_role_policy_attachment" "eks_control_plane_logs" {
  policy_arn = aws_iam_policy.eks_control_plane_logs.arn
  role       = aws_iam_role.dev.name
}
resource "aws_iam_role_policy_attachment" "dev-AmazonEKSClusterPolicy" {
  policy_arn = "arn:aws:iam::aws:policy/AmazonEKSClusterPolicy"
  role       = aws_iam_role.dev.name
}
resource "aws_eks_cluster" "dev" {
  name     = var.cluster_name
  version  = var.cluster_version
  role_arn = aws_iam_role.dev.arn
  vpc_config {
    subnet_ids = [
      aws_subnet.private-1.id,
      aws_subnet.private-2.id,
      aws_subnet.private-3.id,
      aws_subnet.public-1.id,
      aws_subnet.public-2.id,
      aws_subnet.public-3.id
    ]
  }
  #allow control-plane logging
  enabled_cluster_log_types = ["api", "authenticator", "audit", "scheduler", "controllerManager"]
  depends_on                = [aws_iam_role_policy_attachment.dev-AmazonEKSClusterPolicy]
}

# The log group name format is /aws/eks/<cluster-name>/cluster
# Reference: https://docs.aws.amazon.com/eks/latest/userguide/control-plane-logs.html
# https://registry.terraform.io/providers/hashicorp/aws/latest/docs/resources/eks_cluster
resource "aws_cloudwatch_log_group" "eks_control_plane_logs" {
  name              = "/aws/eks/${var.cluster_name}/control-plane-logs"
  retention_in_days = 7
}


#Create kubeconfig
resource "null_resource" "java" {
  depends_on = [helm_release.aws-load-balancer-controller]
  provisioner "local-exec" {
    command = "aws eks --region us-east-1 update-kubeconfig --name $AWS_CLUSTER_NAME"
    environment = {
      AWS_CLUSTER_NAME = var.cluster_name
    }
  }
}