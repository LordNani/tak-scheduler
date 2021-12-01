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
                saveToken(data.data.tokenDTO.token)
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
                saveToken(data.data.token)
            }).catch(err => {
            console.log(err)
        })
    })

    function saveToken(token){
        sessionStorage.setItem("token", token)
    }
}