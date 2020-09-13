const APP_VIEW = document.getElementById('app-view');

window.onload = function() {
    loadLogin();
    document.getElementById('toLogin').addEventListener('click', loadLogin);
    document.getElementById('toRegister').addEventListener('click', loadRegister);
    document.getElementById('toHome').addEventListener('click', loadHome);
    document.getElementById('toLogout').addEventListener('click', logout);
    document.getElementById('toAllUsers').addEventListener('click', loadAllUsers);
    document.getElementById('toAllReimbs').addEventListener('click', loadAllReimbs);
    document.getElementById('toSubmit').addEventListener('click', loadSubmit);
}

//----------------------LOAD VIEWS-------------------------

function loadLogin() {

    console.log('in loadLogin()');

    let xhr = new XMLHttpRequest();

    xhr.open('GET', 'login.view', true); // third parameter (default true) indicates we want to make this req async
    xhr.send();

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            APP_VIEW.innerHTML = xhr.responseText;
            configureLoginView();
        }
    }

}

function loadRegister() {

    console.log('in loadRegister()');

    let xhr = new XMLHttpRequest();

    xhr.open('GET', 'register.view'); // third parameter of this method is optional (defaults to true)
    xhr.send();

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            APP_VIEW.innerHTML = xhr.responseText;
            configureRegisterView();
        }
    }

}

function loadHome() {

    console.log('in loadHome()');

    if (!localStorage.getItem('authUser')) {
        console.log('No user logged in, navigating to login screen');
        loadLogin();
        return;
    }


    let xhr = new XMLHttpRequest();

    xhr.open('GET', 'home.view');
    xhr.send();

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            APP_VIEW.innerHTML = xhr.responseText;
            configureHomeView();
        }
    }

}

function loadAllUsers() {
    
    console.log('in loadAllUsers()');

    if (!localStorage.getItem('authUser')) { // make sure user is logged in. TODO make sure user is admin
        console.log('No user logged in, navigating to login screen');
        loadLogin();
        return;
    }

    let xhr = new XMLHttpRequest();

    xhr.open('GET', 'users.view');
    xhr.send();

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) { // && xhr.status == something (set in UserServlet?)
            APP_VIEW.innerHTML = xhr.responseText;
            configureAllUsersView();
        } 
    }

}

function loadAllReimbs() {
    
    console.log('in loadAllReimbs()');

    if (!localStorage.getItem('authUser')) { // make sure user is logged in. TODO make sure user is admin
        console.log('No user logged in, navigating to login screen');
        loadLogin();
        return;
    }

    let xhr = new XMLHttpRequest();

    xhr.open('GET', 'reimbs.view');
    xhr.send();

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) { // && xhr.status == something (set in UserServlet?)
            APP_VIEW.innerHTML = xhr.responseText;
            configureAllReimbsView();
        } 
    }

}


function loadSubmit() {

    console.log('in loadSubmit()');

    if (!localStorage.getItem('authUser')) { // make sure user is logged in. TODO make sure user is admin
        console.log('No user logged in, navigating to login screen');
        loadLogin();
        return;
    }

    let xhr = new XMLHttpRequest();

    xhr.open('GET', 'submit.view'); // third parameter of this method is optional (defaults to true)
    xhr.send();

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            APP_VIEW.innerHTML = xhr.responseText;
            configureSubmitView(); 
        }
    }

}

//----------------CONFIGURE VIEWS--------------------

function configureLoginView() {

    console.log('in configureLoginView()');

    document.getElementById('login-message').setAttribute('hidden', true);
    document.getElementById('login-button-container').addEventListener('mouseover', validateLoginForm);
    document.getElementById('login').addEventListener('click', login);

}

function configureRegisterView() {

    console.log('in configureRegisterView()');

    document.getElementById('reg-message').setAttribute('hidden', true);

    document.getElementById('reg-username').addEventListener('blur', isUsernameAvailable);
    document.getElementById('email').addEventListener('blur', isEmailAvailable);

    document.getElementById('register').setAttribute('disabled', true);
    document.getElementById('reg-button-container').addEventListener('mouseover', validateRegisterForm);
    document.getElementById('register').addEventListener('click', register);

}

function configureHomeView() {

    let authUser = JSON.parse(localStorage.getItem('authUser'));
    document.getElementById('loggedInUsername').innerText = authUser.username;

}

function configureAllUsersView() {

    console.log('in configureAllUsersView');
    let authUser = JSON.parse(localStorage.getItem('authUser'));
    document.getElementById('loggedInUsername').innerText = authUser.username;

    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'users');
    xhr.send();

    xhr.onreadystatechange = function() {
        if (xhr.readyState = 4 && xhr.status == 200) {
            
            let array = JSON.parse(xhr.responseText); // the response from a GET request to reimbs
            console.log(array);
            let table = document.getElementById("allUsersTable"); // accessing the HTML tag with this ID
            let head = document.createElement("thead"); // create the table head
            let body = document.createElement("tbody"); // creating a tbody element

            table.appendChild(head); // append the head
            head.innerHTML = "<tr>" 
                            + "<th>ID</th>"
                            + "<th>Username</th>"
                            + "<th>Password</th>"
                            + "<th>First Name</th>"
                            + "<th>Last Name</th>"
                            + "<th>Email</th>"
                            + "<th>Role</th>";
                            console.log('test');
            
            table.appendChild(body); // attaching the newly created element to the table that is already in the document

            for (let i=0; i < array.length; i++) { // for every object in the response text...

                let row = document.createElement("tr"); // create a row for that object

                // each row has multiple data cells with information corresponsing the key value pairs in the response text
                row.innerHTML = "<td>" + array[i].id + "</td>" 
                                    + "<td>" + array[i].username + "</td>"
                                    + "<td>" + array[i].password + "</td>"
                                    + "<td>" + array[i].firstName + "</td>"
                                    + "<td>" + array[i].lastName + "</td>"
                                    + "<td>" + array[i].email + "</td>"
                                    + "<td>" + array[i].role + "</td>";
                
                body.appendChild(row); // appoend each row to the body after finding the information about the object
                                
            }
        
        }
    }

}

function configureAllReimbsView() {

    console.log('in configureAllReimbsView');
    let authUser = JSON.parse(localStorage.getItem('authUser'));
    document.getElementById('loggedInUsername').innerText = authUser.username;

    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'reimbs');
    xhr.send();

    xhr.onreadystatechange = function() {
        if (xhr.readyState = 4 && xhr.status == 200) {

            var array = JSON.parse(xhr.responseText); // the response from a GET request to reimbs
            let table = document.getElementById("allReimbsTable"); // accessing the HTML tag with this ID
            let head = document.createElement("thead"); // create the table head
            let body = document.createElement("tbody"); // creating a tbody element

            table.appendChild(head); // append the head
            head.innerHTML = "<tr>" 
                            + "<th>ID</th>"
                            + "<th>Author</th>"
                            + "<th>Description</th>"
                            + "<th>Amount</th>"
                            + "<th>Submitted</th>"
                            + "<th>Type</th>"
                            + "<th>Status</th>"
                            + "<th>Resolved</th>"
                            + "<th>Resolver</th>";
            
            table.appendChild(body); // attaching the newly created element to the table that is already in the document

            for (let i=0; i < array.length; i++) { // for every object in the response text...

                let row = document.createElement("tr"); // create a row for that object

                // each row has multiple data cells with information corresponsing the key value pairs in the response text
                row.innerHTML = "<td>" + array[i].id + "</td>" 
                                    + "<td>" + array[i].authorId + "</td>"
                                    + "<td>" + array[i].description + "</td>"
                                    + "<td>" + array[i].amount + "</td>"
                                    + "<td>" + array[i].submitted + "</td>"
                                    + "<td>" + array[i].reimbursementType + "</td>"
                                    + "<td>" + array[i].reimbursementStatus + "</td>"
                                    + "<td>" + array[i].resolved + "</td>"
                                    + "<td>" + array[i].resolverId + "</td>";
                
                body.appendChild(row); // append each row to the body after finding the information about the object
                                
            }

            
            //JQUERY
            $(document).ready( function () {
                $('#AllReimbsTable').DataTable();
                } );(jQuery);
        }
    }

}


function configureSubmitView() {

    console.log('in configureSubmitView()');

    document.getElementById('submit-message').setAttribute('hidden', true);

    document.getElementById('submit').setAttribute('disabled', true);
    document.getElementById('submit-button-container').addEventListener('mouseover', validateSubmitForm);
    document.getElementById('submit').addEventListener('click', submit);

}

//------------------OPERATIONS-----------------------

function login() {

    console.log('in login()');

    let un = document.getElementById('login-username').value;
    let pw = document.getElementById('login-password').value;

    let credentials = {
        username: un,
        password: pw
    }

    let credentialsJSON = JSON.stringify(credentials);

    let xhr = new XMLHttpRequest();

    xhr.open('POST', 'auth');
    xhr.setRequestHeader('Content-type', 'application/json');
    xhr.send(credentialsJSON);

    xhr.onreadystatechange = function () {

        if (xhr.readyState == 4 && xhr.status == 200) {

            document.getElementById('login-message').setAttribute('hidden', true);
            localStorage.setItem('authUser', xhr.responseText);
            loadHome();

        } else if (xhr.readyState == 4 && xhr.status == 401) {

            document.getElementById('login-message').removeAttribute('hidden');
            let err = JSON.parse(xhr.responseText);
            document.getElementById('login-message').innerText = err.message;

        }

    }

}

function register() {

    console.log('in register()');

    let fn = document.getElementById('fn').value;
    let ln = document.getElementById('ln').value;
    let email = document.getElementById('email').value;
    let un = document.getElementById('reg-username').value;
    let pw = document.getElementById('reg-password').value;

    let newUser = {
        firstName: fn,
        lastName: ln,
        email: email,
        username: un,
        password: pw
    }

    let newUserJSON = JSON.stringify(newUser);

    let xhr = new XMLHttpRequest();

    xhr.open('POST', 'users');
    xhr.send(newUserJSON);

    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 201) {
            loadLogin();
        } else if (xhr.readyState == 4 && xhr.status != 201) {
            document.getElementById('reg-message').removeAttribute('hidden');
            let err = JSON.parse(xhr.responseText);
            document.getElementById('reg-message').innerText = err.message;
        }
    }
}

function submit() {

    console.log('in submit()');

    let amount = document.getElementById('amount').value;
    let type = document.getElementById('type').value;
    let description = document.getElementById('description').value;
    let authUser = JSON.parse(localStorage.getItem('authUser')); // authUser is the currentUser logged in. Use this to find authorId field of reimbursement object

    let newReimb = {
        amount: amount,
        reimbursementType: type,
        description: description,
        submitted: new Date(),
        authorId: authUser.id
    }

    let newReimbJSON = JSON.stringify(newReimb);

    let xhr = new XMLHttpRequest();

    xhr.open('POST', 'reimbs');
    xhr.send(newReimbJSON);

    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 201) {
            loadHome();
        } else if (xhr.readyState == 4 && xhr.status != 201) {
            document.getElementById('submit-message').removeAttribute('hidden');
            let err = JSON.parse(xhr.responseText);
            document.getElementById('submit-message').innerText = err.message;
        }
    }


}

function logout() {

    console.log('in logout()');

    let xhr = new XMLHttpRequest();

    xhr.open('GET', 'auth');
    xhr.send();

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 204) {
            console.log('logout successful!');
            localStorage.removeItem('authUser');
            loadLogin();
        }
    }
}

function isUsernameAvailable() {

    console.log('in isUsernameAvailable()');

    let username = document.getElementById('reg-username').value;

    if (!username) {
        return;
    }

    let xhr = new XMLHttpRequest();

    xhr.open('POST', 'username.validate');
    xhr.setRequestHeader('Content-type', 'application/json');
    xhr.send(JSON.stringify(username));

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 204) {
            console.log('Provided username is available!');
            document.getElementById('reg-message').setAttribute('hidden', true);
        } else if (xhr.readyState == 4 && xhr.status == 409 ) {
            document.getElementById('reg-message').removeAttribute('hidden')
            document.getElementById('reg-message').innerText = 'The provided username is already taken!';
            document.getElementById('register').setAttribute('disabled', true);
        }
    }

}

function isEmailAvailable() {

    console.log('in isEmailAvailable()');

    let email = document.getElementById('email').value;

    if (!email) {
        return;
    }

    let xhr = new XMLHttpRequest();

    xhr.open('POST', 'email.validate');
    xhr.setRequestHeader('Content-type', 'application/json');
    xhr.send(JSON.stringify(email));

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 204) {
            console.log('Provided email is available!');
            document.getElementById('reg-message').setAttribute('hidden', true);
        } else if (xhr.readyState == 4 && xhr.status == 409) {
            document.getElementById('reg-message').removeAttribute('hidden');
            document.getElementById('reg-message').innerText = 'The provided email address is already taken!';
            document.getElementById('register').setAttribute('disabled', true);
        }
    }
}


//---------------------FORM VALIDATION-------------------------

function validateLoginForm() {

    console.log('in validateLoginForm()');

    let msg = document.getElementById('login-message').innerText;

    if (msg == 'User authentication failed!') {
        return;
    }

    let un = document.getElementById('login-username').value;
    let pw = document.getElementById('login-password').value;

    if (!un || !pw) {
        document.getElementById('login-message').removeAttribute('hidden');
        document.getElementById('login-message').innerText = 'You must provided values for all fields in the form!';
        document.getElementById('login').setAttribute('disabled', true);
    } else {
        document.getElementById('login').removeAttribute('disabled');
        document.getElementById('login-message').setAttribute('hidden', true);
    }

}

function validateRegisterForm() {

    console.log('in validateRegisterForm()');

    let fn = document.getElementById('fn').value;
    let ln = document.getElementById('ln').value;
    let email = document.getElementById('email').value;
    let un = document.getElementById('reg-username').value;
    let pw = document.getElementById('reg-password').value;

    if (!fn || !ln || !email || !un || !pw) {
        document.getElementById('reg-message').removeAttribute('hidden');
        document.getElementById('reg-message').innerText = 'You must provided values for all fields in the form!'
        document.getElementById('register').setAttribute('disabled', true);
    } else {
        document.getElementById('register').removeAttribute('disabled');
        document.getElementById('reg-message').setAttribute('hidden', true);
    }


}

function validateSubmitForm() {

    console.log('in validateSubmitForm()');

    let amount = document.getElementById('amount').value;
    let type = document.getElementById('type').value;
    let description = document.getElementById('description').value;

    if (!amount || !type || !description) {
        document.getElementById('submit-message').removeAttribute('hidden');
        document.getElementById('submit-message').innerText = 'You must provided values for all fields in the form!'
        document.getElementById('submit').setAttribute('disabled', true);
    } else {
        document.getElementById('submit').removeAttribute('disabled');
        document.getElementById('submit-message').setAttribute('hidden', true);
    }


}