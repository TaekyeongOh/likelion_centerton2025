import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Vector from '../../assets/img/Vector 5.png'
import Menu1_img from '../../assets/img/image 4.png'
import QR from '../../assets/img/bx_qr.png'
import Language from '../../assets/img/flowbite_language-outline.png'
import Order_list from '../../assets/img/Frame 8.png'
import Shopping from '../../assets/img/Frame 258.png'
import Table from '../../assets/img/Frame 320.png'


const Menu1 = () => {
  const navigate = useNavigate();
  const [count, setCount] = useState(1);
  const [selected1, setSelected1] = useState(false);
  const [selected2, setSelected2] = useState(false);

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
              <img src={QR} alt="QR 코드" />
              </button>
            </div>
            <div className="function2">
              <button><img src={Language} alt="" /></button>
            </div>
          </div>
        </div>
        <div className="Menu_name">
          <h2>MENU</h2>
          <h1>소고기 미역국 정식</h1>
          <p>13,000원</p>
        </div>
        <div className="Menu_detail">
          <img src={Menu1_img} alt="" />
          <p>부드러운 소고기와 진한 국물의<br />
          미역국 + 밥 + 반찬 3종. 맵지 않아요!</p>
          <div className="detail_box">
            <button className={`detail_1 ${selected1 ? 'selected' : ''}`}
            onClick={() => setSelected1(!selected1)}>
           <p>일본인 인기 No.1 메뉴</p>
           </button>
          <button className={`detail_2 ${selected2 ? 'selected' : ''}`}
            onClick={() => setSelected2(!selected2)}>
            <p>비건 변경 가능</p>
          </button>
          </div>

          <div className="quantity">
            <div className="quantity_count">
            <span>개수</span>
            <div>
              <button onClick={() => count > 1 && setCount(count - 1)}>-</button>
              <span>{count}</span>
              <button onClick={() => setCount(count + 1)}>+</button>
              </div>
            </div>
            <div className="order_list">
              <button><img src={Order_list} alt="" /></button>
            </div></div>
        </div>
        
        <div className="shop">
          <button><img src={Table} alt="" /></button>
        </div>

        <div className="shopping">
          <button><img src={Shopping} alt="" /></button>
        </div>

        
      </div>
    </div>
    </div>
  )
}

export default Menu1
