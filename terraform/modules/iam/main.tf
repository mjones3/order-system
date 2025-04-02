# 1) An IAM policy document that allows ECS tasks to assume the role.
data "aws_iam_policy_document" "ecs_task_execution_assume_role" {
  statement {
    effect  = "Allow"
    actions = ["sts:AssumeRole"]
    principals {
      type        = "Service"
      identifiers = ["ecs-tasks.amazonaws.com"]
    }
  }
}

# 2) The IAM role that the ECS task will assume.
resource "aws_iam_role" "ecs_task_execution_role" {
  name               = "ecsTaskExecution"
  assume_role_policy = data.aws_iam_policy_document.ecs_task_execution_assume_role.json
}

# 3) Attach the AWS-managed policy that covers:
#    - Pulling images from ECR
#    - Sending logs to CloudWatch
resource "aws_iam_role_policy_attachment" "ecs_task_execution_role_policy_attachment" {
  role       = aws_iam_role.ecs_task_execution_role.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy"
}
