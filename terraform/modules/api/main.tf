resource "aws_api_gateway_rest_api" "order" {
  name = "orders"
}

resource "aws_api_gateway_resource" "order" {
  parent_id   = aws_api_gateway_rest_api.order.root_resource_id
  path_part   = "order"
  rest_api_id = aws_api_gateway_rest_api.order.id
}

resource "aws_api_gateway_method" "order" {
  authorization = "NONE"
  http_method   = "GET"
  resource_id   = aws_api_gateway_resource.order.id
  rest_api_id   = aws_api_gateway_rest_api.order.id
}

resource "aws_api_gateway_integration" "order" {
  http_method = aws_api_gateway_method.order.http_method
  resource_id = aws_api_gateway_resource.order.id
  rest_api_id = aws_api_gateway_rest_api.order.id
  type        = "MOCK"
}

resource "aws_api_gateway_deployment" "order" {
  rest_api_id = aws_api_gateway_rest_api.order.id

  triggers = {
    # NOTE: The configuration below will satisfy ordering considerations,
    #       but not pick up all future REST API changes. More advanced patterns
    #       are possible, such as using the filesha1() function against the
    #       Terraform configuration file(s) or removing the .id references to
    #       calculate a hash against whole resources. Be aware that using whole
    #       resources will show a difference after the initial implementation.
    #       It will stabilize to only change when resources change afterwards.
    redeployment = sha1(jsonencode([
      aws_api_gateway_resource.order.id,
      aws_api_gateway_method.order.id,
      aws_api_gateway_integration.order.id,
    ]))
  }

  lifecycle {
    create_before_destroy = true
  }
}

resource "aws_api_gateway_stage" "order" {
  deployment_id = aws_api_gateway_deployment.order.id
  rest_api_id   = aws_api_gateway_rest_api.order.id
  stage_name    = "order"
}

