import schemathesis
import pytest

schema = schemathesis.from_uri("https://raw.githubusercontent.com/OAI/OpenAPI-Specification/main/examples/v3.0/petstore.yaml")

@schema.parametrize()
def test_api(case):
    response = case.call()
    case.validate_response(response)
