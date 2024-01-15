module "eks_blueprints_addons" {
  source  = "aws-ia/eks-blueprints-addons/aws"
  version = "~> 1.0" # ensure to update this to the latest/desired version

  cluster_name      = var.cluster_name
  cluster_endpoint  = aws_eks_cluster.dev.endpoint
  cluster_version   = var.cluster_version
  oidc_provider_arn = data.aws_eks_cluster.dev.identity[0].oidc[0].issuer

#   enable_velero = true
#   velero = {
#     s3_backup_location = "bucket-s3-terraform-orderhere-velero/*"
#   }

  helm_releases = {
    velero = {
      name             = "velero"
      namespace        = "velero"
      create_namespace = true
      chart            = "velero"
      repository       = "https://vmware-tanzu.github.io/helm-charts"
      values = [templatefile("velerovalues.yaml", { ROLE = aws_iam_role.velero-backup-role.arn })]
    }
  }

  tags = {
    Environment = "dev"
  }
}


resource "aws_iam_policy" "velero-backup" {
  name = "velero-backup-policy-${var.cluster_name}"

  policy = jsonencode(
    {
      "Version" : "2012-10-17",
      "Statement" : [
        {
          "Effect" : "Allow",
          "Action" : [
            "ec2:DescribeVolumes",
            "ec2:DescribeSnapshots",
            "ec2:CreateTags",
            "ec2:CreateVolume",
            "ec2:CreateSnapshot",
            "ec2:DeleteSnapshot"
          ],
          "Resource" : "*"
        },
        {
          "Effect" : "Allow",
          "Action" : [
            "s3:GetObject",
            "s3:DeleteObject",
            "s3:PutObject",
            "s3:AbortMultipartUpload",
            "s3:ListMultipartUploadParts"
          ],
          "Resource" : [
            "arn:aws:s3:::bucket-s3-terraform-orderhere-velero/*"
          ]
        },
        {
          "Effect" : "Allow",
          "Action" : [
            "s3:ListBucket"
          ],
          "Resource" : [
            "arn:aws:s3:::bucket-s3-terraform-orderhere-velero/*",
            "arn:aws:s3:::bucket-s3-terraform-orderhere-velero"
          ]
        }
      ]
    }
  )
}

resource "aws_iam_role" "velero-backup-role" {
  name = "velero-backup-role-${var.cluster_name}"
  assume_role_policy = jsonencode(
    {
      "Version" : "2012-10-17",
      "Statement" : [
        {
          "Effect" : "Allow",
          "Principal" : {
            Federated = "arn:aws:iam::${data.aws_caller_identity.dev.account_id}:oidc-provider/${replace(data.aws_eks_cluster.dev.identity[0].oidc[0].issuer, "https://", "")}"
          },
          "Action" : "sts:AssumeRoleWithWebIdentity",
          "Condition" : {
            "StringEquals" : {
              "${replace(data.aws_eks_cluster.dev.identity[0].oidc[0].issuer, "https://", "")}:sub" = "system:serviceaccount:velero:velero-server"
              "${replace(data.aws_eks_cluster.dev.identity[0].oidc[0].issuer, "https://", "")}:aud" = "sts.amazonaws.com"
            }
          }
        }
      ]
    }
  )
}

resource "aws_iam_role_policy_attachment" "velero_policy_attachment" {
  policy_arn = aws_iam_policy.velero-backup.arn
  role       = aws_iam_role.velero-backup-role.name
}




