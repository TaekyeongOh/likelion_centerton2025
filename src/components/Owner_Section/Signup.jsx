import { useNavigate } from 'react-router-dom';
import Logo from '../../assets/img/Union.png'
import Signup_button from '../../assets/img/signup .png'
import Text from '../../assets/img/EATO (1).png'

const Signup = () => {
  const navigate = useNavigate();

  return (

    <div>
      <div className="Signup_wrap">
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
      <div className="input_check">
        <p>비밀번호 확인</p>
        <input type="password" name="password_check" placeholder="비밀번호를 다시 입력해주세요." required />
      </div>
      </div>
      <div className="start_button">
        <button onClick={() => navigate('/login')}>
        <img src={Signup_button} alt="" />
        </button>
        <p>이미 EATO의 회원이신가요?</p>
        <a href='Login' class="login_link">로그인</a>
      </div>

      </div>
     </div>
  )
}

export default Signup
