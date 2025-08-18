import { useNavigate } from 'react-router-dom';
import Logo from '../../assets/img/Union.png'
import Login_button from '../../assets/img/login .png'
import Text from '../../assets/img/EATO .png'

const Login = () => {
  const navigate = useNavigate();

  return (

    <div>
      <div className="Login_wrap">
       <div className="logo">
        <img src={Logo} alt="" />
      </div>
      <div className="text">
        <img src={Text} alt="" />
      </div>
      <div className="input">
      <div className="input_email">
        <p>이메일</p>
        <input type="text" name="email" placeholder="이메일을 입력해주세요." required />
      </div>
      <div className="input_password">
        <p>비밀번호</p>
        <input type="password" name="password" placeholder="비밀번호를 입력해주세요." required />
      </div>
      </div>
      <div className="start_button">
        <button onClick={() => navigate('/Login')}>
        <img src={Login_button} alt="" />
        </button>
        <p>아직 EATO의 회원이 아니신가요?</p>
        <a href='Signup' class="signup_link">회원가입</a>
      </div>

      </div>
     </div>
  )
}

export default Login
