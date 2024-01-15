provider "aws" {
  region = var.region
  alias  = "us_east_1"
}

provider "aws" {
  region = "us-east-1"
  alias  = "certificates"
}

provider "aws" {
  region = "us-east-1"
  alias  = "dns"
}

module "cert" {
  source = "github.com/azavea/terraform-aws-acm-certificate"

  providers = {
    aws.acm_account     = aws.certificates
    aws.route53_account = aws.dns
  }

  domain_name                       = var.r53_record_name
  subject_alternative_names         = ["*.${var.r53_record_name}"]
  hosted_zone_id                    = var.r53_zone_id
  validation_record_ttl             = "60"
  allow_validation_record_overwrite = true
}
