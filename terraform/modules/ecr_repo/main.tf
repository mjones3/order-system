

module "ecr_repo" {
  source = "terraform-aws-modules/ecr/aws"

  repository_name = var.repository_name
  # image_tag_mutability = "MUTABLE"

  # from the variable
  repository_read_write_access_arns = [
    var.ecs_task_execution_role_arn
  ]

  repository_lifecycle_policy = jsonencode({
    rules = [
      {
        rulePriority = 1
        description  = "Keep last 30 images"
        selection = {
          tagStatus     = "tagged"
          tagPrefixList = ["v"]
          countType     = "imageCountMoreThan"
          countNumber   = 30
        }
        action = {
          type = "expire"
        }
      }
    ]
  })

  tags = {
    Terraform   = "true"
    Environment = "dev"
  }
}


# VPC Endpoint for ECR API
resource "aws_vpc_endpoint" "ecr_api" {
  vpc_id             = var.vpc_id
  service_name       = "com.amazonaws.us-east-1.ecr.api"
  vpc_endpoint_type  = "Interface"
  subnet_ids         = var.private_subnets
  security_group_ids = [var.vpc_endpoint_sg] # Ensure this SG allows HTTPS outbound traffic
}

# VPC Endpoint for ECR DKR
resource "aws_vpc_endpoint" "ecr_dkr" {
  vpc_id             = var.vpc_id
  service_name       = "com.amazonaws.us-east-1.ecr.dkr"
  vpc_endpoint_type  = "Interface"
  subnet_ids         = var.private_subnets
  security_group_ids = [var.vpc_endpoint_sg]
}

# VPC Endpoint for ECR DKR
resource "aws_vpc_endpoint" "s3" {
  vpc_id             = var.vpc_id
  service_name       = "com.amazonaws.us-east-1.s3"
  vpc_endpoint_type  = "Interface"
  subnet_ids         = var.private_subnets
  security_group_ids = [var.vpc_endpoint_sg]
}
