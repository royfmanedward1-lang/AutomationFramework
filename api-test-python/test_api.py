import requests

def test_get_user():
    response = requests.get('https://jsonplaceholder.typicode.com/users/1')
    assert response.status_code == 200
    data = response.json()
    assert data['id'] == 1
    assert 'name' in data

def test_create_post():
    payload = {'title': 'foo', 'body': 'bar', 'userId': 1}
    response = requests.post('https://jsonplaceholder.typicode.com/posts', json=payload)
    assert response.status_code == 201
    data = response.json()
    assert data['title'] == 'foo'
    assert data['body'] == 'bar'
