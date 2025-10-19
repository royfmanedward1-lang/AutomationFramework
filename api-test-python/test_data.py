from pydantic import BaseModel
import pytest

class User(BaseModel):
    id: int
    name: str
    email: str

@pytest.fixture
def user_data():
    return User(id=1, name="Alice", email="alice@example.com")

def test_user_data(user_data):
    assert user_data.id == 1
    assert user_data.name == "Alice"
