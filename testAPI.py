import json
import urllib2
import ast

def main():
    user = makeUserObject("James", "Courson", "jc@DailyBagel.com",
                          "root", "Admin", "asdfjkl;")

    addUser(user)



def makeUserObject(firstName, lastName, email, password, role, token):
    return {
        "firstName": firstName,
        "lastName": lastName,
        "email": email,
        "password": password,
        "role": role,
        "token": token
        }

def addUser(user):        
    req = urllib2.Request('http://localhost:8080/addUser')
    req.add_header('Content-Type', 'application/json')
    response = urllib2.urlopen(req, json.dumps(user))
    f = response.read()
    print(f)

def getUser(id):
    req = urllib2.Request('http://localhost:8080/getUser?userId=' + str(id))
    response = urllib2.urlopen(req)
    return response

def deleteUser(id):
    req = urllib2.Request('http://localhost:8080/deleteUser')
    req.add_header('Content-Type', 'application/json')
    response = urllib2.urlopen(req, json.dumps(data))
    f = response.read()
    print(f)


    

try:
    main()
except urllib2.HTTPError as e:
    error = e.read()
    print(error)
