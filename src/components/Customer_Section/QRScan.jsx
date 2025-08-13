import { useNavigate } from 'react-router-dom';
import Vector from '../../assets/img/Vector 5.png'
import QRImg from '../../assets/img/bx_qr (1).png'
import Language from '../../assets/img/flowbite_language-outline.png'
import QRPage from '../../assets/img/Frame 19.png'



const QRScan = () => {
  const navigate = useNavigate();

  return (
    <div>
      <div className="Menu_wrap">
      <div className="Menu1_wrap">
         
        <div className="nav">
         <button className="vector" onClick={() => navigate(-1)}>
          <img src={Vector} alt="뒤로가기" />
          </button>

          <div className="function">
            <div className="function1">
              <button onClick={() => navigate('/qr')}>
              <img src={QRImg} alt="QR 코드" />
              </button>
            </div>
            <div className="function2">
              <button><img src={Language} alt="" /></button>
            </div>
          </div>
        </div>
        <div className="Menu_name">
          <h2>QR SCAN</h2>
          <h1>QR을 스캔해주세요!</h1>
        </div>
        <div className="Menu_detail">
          <img src={QRPage} alt="" />
          <p>QR코드가 인식되면 자동으로<br />
          메뉴판으로 넘어가요.</p>
          

          
        


        
      </div></div>
    </div>
    </div>
  )
}

export default QRScan
