const APP_VIEW = document.getElementById('app-view');

window.onload = function() {
    loadLogin();
    console.log('test beginning');
    document.getElementById('toLogin').addEventListener('click', loadLogin);
    document.getElementById('toRegister').addEventListener('click', loadRegister);
    document.getElementById('toHome').addEventListener('click', loadHome);
    document.getElementById('toLogout').addEventListener('click', logout);
    document.getElementById('toAllUsers').addEventListener('click', loadAllUsers);
    document.getElementById('toAllReimbs').addEventListener('click', loadAllReimbs);
    document.getElementById('toSubmit').addEventListener('click', loadSubmit);
    document.getElementById('toAuthorReimbs').addEventListener('click', loadAuthorReimbs);
}

// //JQUERY
// $(document).ready( function () {
//     $('#reimbsTable').DataTable();
//     } );(jQuery);

// $(document).ready( function () {
//     $('#AllUsersTable').DataTable();
//     } );(jQuery);

//     $(document).ready( function () {
//         $('#test-table').DataTable();
//      } );(jQuery);



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
            console.log('after onreadystatechange');
            APP_VIEW.innerHTML = xhr.responseText;
            console.log('after loading the username from the response text');
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
            // document.getElementById('findUserToEdit').addEventListener('click', findUserToEdit);
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

function loadAuthorReimbs() {

    console.log('in loadAuthorReimbs()');

    if (!localStorage.getItem('authUser')) {
        console.log('No user logged in, navigating to login screen');
        loadLogin();
        return;
    }

    let xhr = new XMLHttpRequest();

    xhr.open('GET', 'reimbs.view');
    xhr.send();

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            APP_VIEW.innerHTML = xhr.responseText;
            configureAuthorReimbsView();
        }
    }


}

function loadReimbDetails() {

    console.log('in loadReimbDetails()');

    if (!localStorage.getItem('authUser')) {
        console.log('No user logged in, navigating to login screen');
        loadLogin();
        return;
    }

    let xhr = new XMLHttpRequest();

    xhr.open('GET', 'reimbs');
    xhr.send();

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            
            let array = JSON.parse(xhr.responseText);
            console.log(array);

            APP_VIEW.innerHTML = "<h1>Reimb Details:</h1>"
                                + "<div class='form-group'>"
                                + "<label for='approveOrDeny'> Enter approve or deny</label>"
                                + "<input type='text' class='form-control' id='approveOrDeny' placeholder='Ex: approve'>"
                                + "</div>"
                                + "<div id='reimb-button-container'>"
                                + "<button type='submit' class='btn btn-primary' id='approveItOrDenyIt'>Choose</button>"
                                + "<h3> ID:" + array.id + "</h3>"
                                + "<h3> Amount:" + array.amount + "</h3>"
                                + "<h3> Submitted:" + array.submitted + "</h3>"
                                + "<h3> Resolved:" + array.resolved + "</h3>"
                                + "<h3> Description:" + array.description + "</h3>"
                                + "<h3> Author:" + array.authorId + "</h3>"
                                + "<h3> Resolver:" + array.resolverId + "</h3>"
                                + "<h3> Status:" + array.reimbursementStatus + "</h3>"
                                + "<h3> Type:" + array.reimbursementType + "</h3>";
                                // TODO add button here to approve or deny
                document.getElementById('approveItOrDenyIt').addEventListener('click', approveItOrDenyIt);


        }
    }

}

function loadUpdateUser() {

    console.log('in loadUpdateUser()');

    let xhr = new XMLHttpRequest();

    xhr.open('GET', 'updateuser.view'); // third parameter of this method is optional (defaults to true)
    xhr.send();

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            APP_VIEW.innerHTML = xhr.responseText;
            configureUpdateUserView();
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

    console.log('in configureHomeView');

    let authUser = JSON.parse(localStorage.getItem('authUser'));
    let options = document.getElementById('role-specific-options');

    document.getElementById('loggedInUsername').innerText = authUser.username;

    console.log(authUser.role);
    console.log('User role should be logged above');

    // if the authUser's role is admin, configure the admin page
    if (authUser.role == 'Admin') {

        console.log('user is an admin! Configuring admin view');

        options.innerHTML = "<a class='nav-item nav-link' id='toRegister'>Register</a>"
                            + "<a class='nav-item nav-link' id='toAllUsers'>All Users</a>"; // add these to the div

    } else if (authUser.role == 'Finance Manager') {

        console.log('user is a finance manager! Configuring manager view');

        options.innerHTML = "<a class='nav-item nav-link' id='toAllReimbs'>All Reimbursements</a>";

        document.getElementById('toAllReimbs').addEventListener('click', loadAllReimbs);

    } else if (authUser.role == 'Employee') {

        console.log ('user is an employee! Configuring employee view');

        options.innerHTML = "<a class='nav-item nav-link' id='toSubmit'>Submit</a>"
                            + "<a class='nav-item nav-link' id='toAuthorReimbs'>My Reimbursements</a>";

    }

}

function configureAllUsersView() {

    console.log('in configureAllUsersView');
    let authUser = JSON.parse(localStorage.getItem('authUser'));
    document.getElementById('loggedInUsername').innerText = authUser.username;
    document.getElementById('findUserToEdit').addEventListener('click', findUserToEdit);

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
    document.getElementById('viewReimbDetails').addEventListener('click', findReimbDetails);

    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'reimbs');
    xhr.send();

    xhr.onreadystatechange = function() {
        if (xhr.readyState = 4 && xhr.status == 200) {

            var array = JSON.parse(xhr.responseText); // the response from a GET request to reimbs
            let table = document.getElementById("reimbsTable"); // accessing the HTML tag with this ID
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

function configureAuthorReimbsView() {

    console.log('in configureAuthorReimbsView()');

    let authUser = JSON.parse(localStorage.getItem('authUser'));
    document.getElementById('loggedInUsername').innerText = authUser.username;
    document.getElementById('viewReimbDetails').addEventListener('click', findReimbDetails);

    console.log(authUser.id);

    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'reimbs');
    xhr.send();

    xhr.onreadystatechange = function() {
        if (xhr.readyState = 4 && xhr.status == 200) {

            var array = JSON.parse(xhr.responseText); // the response from a GET request to reimbs
            let table = document.getElementById("reimbsTable"); // accessing the HTML tag with this ID
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

            
            
        }
    }

}

function configureUpdateUserView() {

    console.log('in configureUpdateUserView()');

    document.getElementById('update-message').setAttribute('hidden', true);

    document.getElementById('username-update').addEventListener('blur', isUsernameAvailable);
    document.getElementById('email-update').addEventListener('blur', isEmailAvailable);

    // document.getElementById('update').setAttribute('disabled', true);
    // document.getElementById('update-button-container').addEventListener('mouseover', validateUpdateForm);
    document.getElementById('update').addEventListener('click', updateUser);

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
            loadHome(); // user should be logged in as admin to make a new user. So, return to home after done registering new user
        } else if (xhr.readyState == 4 && xhr.status != 201) {
            document.getElementById('reg-message').removeAttribute('hidden');
            let err = JSON.parse(xhr.responseText);
            document.getElementById('reg-message').innerText = err.message;
        }
    }
}

function updateUser() {

    console.log('in updateUser()');

    let newfn = document.getElementById('fn-update').value;
    let newln = document.getElementById('ln-update').value;
    let newEmail = document.getElementById('email-update').value;
    let newun = document.getElementById('username-update').value;
    let newpw = document.getElementById('password-update').value;

    let oldUser = localStorage.getItem('userToUpdate'); // the userToUpdate was set in findUserToUpdate()

    // if any of the fields in the update form are null, assign the original user fields to that field.
    if (newfn == null) {
        newfn = oldUser.firstName;
    }
    if (newln == null) {
        newln = oldUser.lastName;
    }
    if (newEmail == null) {
        newEmail = oldUser.email;
    }
    if (newun == null) {
        newun = oldUser.username;
    }
    if (newpw == null) {
        newpw = oldUser.password;
    }

    // this will have either the values entered in the form or the old information if nothing was entered
    let updatedUser = {
        firstName: newfn,
        lastName: newln,
        email: newEmail,
        username: newun,
        password: newpw
    }

    let updatedUserJSON = JSON.stringify(updatedUser);

    let xhr = new XMLHttpRequest();

    xhr.open('POST', 'users');
    xhr.send(updatedUserJSON);

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 201) { // 201 CREATED bc new data? 
            loadHome(); // loadHome after updating a user? Could also load the allUsers again
        } else if (xhr.readyState == 4 && xhr.status != 201) {
            document.getElementById('update-message').removeAttribute('hidden'); // to make an attribute not hidden. TODO could use this for navbar depending on user role?
            let err = JSON.parse(xhr.responseText);
            document.getElementById('update-message').innerText = err.message;
        }
    }


}

function findReimbDetails() {

    console.log('in findReimbDetails()');

    let id = document.getElementById('reimbId').value;

    let reimb = {
        id: id
    }

    let reimbJSON = JSON.stringify(reimb);
    console.log(reimbJSON);
    console.log(reimbJSON.id);

    let xhr = new XMLHttpRequest();

    xhr.open('POST', 'auth'); // needs to be a post so that the body of send method will not be ignored
    xhr.send(reimbJSON);

    xhr.onreadystatechange = function () {
        
        if (xhr.readyState == 4 && xhr.status == 200) { // 201 created bc a new object will be created from the reimbId sent even though the reimb already exists.
            localStorage.setItem('authReimb', reimb);
            console.log(reimb);
            loadReimbDetails(); // this function sent the ID. In the servlet, this should create an object to be used in the Reimb servlet(?) 
        }

    }

}

function findUserToEdit() {

    console.log('in findUserToEdit()');

    let id = document.getElementById('userId').value;
    console.log(id);

    let user = {
        id: id
    }

    let userJSON = JSON.stringify(user);
    console.log(userJSON);
    console.log(userJSON.id);

    let xhr = new XMLHttpRequest();

    xhr.open('POST', 'auth');
    xhr.send(userJSON);

    xhr.onreadystatechange = function() {

        if (xhr.readyState == 4 && xhr.status == 200) {
            localStorage.setItem('userToUpdate', user);
            console.log(user);
            loadUpdateUser();
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

function approveItOrDenyIt() {

    console.log('in approveItOrDenyIt()');

    let choice = document.getElementById('approveOrDeny').value;
    let authUser = JSON.parse(localStorage.getItem('authUser'));
    let statusId = 1; // initialize statusId to 1

    // make the choice 2 or 3 depending on approve or deny
    if (choice == 'approve') {
        console.log('approve chosen!');
        statusId = 2;
    }
    if (choice == 'deny') {
        console.log('deny chosen!');
        statusId = 3;
    }

    let reimbResolverUpdates = {
        resolved: new Date(),
        resolverId: authUser.id,
        reimbursementStatus: statusId
    }

    let reimbResolverUpdatesJSON = JSON.stringify(reimbResolverUpdates);

    let xhr = new XMLHttpRequest();

    xhr.open('POST', 'reimbs');
    xhr.send(reimbResolverUpdatesJSON);

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 201) {
            loadHome();
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

