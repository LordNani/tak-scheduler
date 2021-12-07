window.onload = () =>{
    const reg_form = document.querySelector('.register-form')
    reg_form.addEventListener('submit', (e)=>{
        const signupData = {
            username: reg_form.querySelector('[name="username"]').value,
            password: reg_form.querySelector('[name="password"]').value,
            fullName: reg_form.querySelector('[name="fullName"]').value
        }
        e.preventDefault()
        // console.log("hello")
        fetch("/api/users/sign-up", {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(signupData)
        })
            .then(async res => {
                const data = await res.json()
                if(res.ok) {
                    saveToken(data.data.tokenDTO.token)
                }
                else{
                    if(data.password != null) alert("Password: "+data.password)
                    if(data.username != null) alert("Username: "+data.username)
                    if(data.message != null) alert(data.message)
                }
            }).catch(err => {
            console.log(err)
        })
    })

    const login_form = document.querySelector('.login-form')
    login_form.addEventListener('submit', (e)=>{
        const signinData = {
            username: login_form.querySelector('[name="username"]').value,
            password: login_form.querySelector('[name="password"]').value,
        }
        e.preventDefault()
        fetch("/api/users/sign-in", {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(signinData)
        })
            .then(async res => {
                const data = await res.json()
                if(res.ok) {
                    saveToken(data.data.token)
                }
                else{
                    if(data.password != null) alert("Password: "+data.password)
                    if(data.username != null) alert("Username: "+data.username)
                    if(data.message != null) alert(data.message)
                }
            }).catch(err => {
            console.log(err)
        })
    })

    function saveToken(token){
        document.cookie=`token=${token}`
        sessionStorage.setItem("token", token)
        document.location = "/view"
    }
}
//
// XMLHttpRequest.prototype.open = (function(open) {
//     return function(method,url,async) {
//         open.apply(this,arguments);
//         if(sessionStorage.getItem("token") != null){
//             this.setRequestHeader('Authorization', sessionStorage.getItem("token"));
//         }
//     };
// })(XMLHttpRequest.prototype.open);