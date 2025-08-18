import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Logo from '../../assets/img/Union.png'
import Start from '../../assets/img/Frame 1.png'
import Text from '../../assets/img/EATO (2).png'

const Store_info = () => {
  const navigate = useNavigate();
  const [count, setCount] = useState(1);
  const [selected1, setSelected1] = useState(false);
  const [selected2, setSelected2] = useState(false);
  const [selected3, setSelected3] = useState(false);
  const [selected4, setSelected4] = useState(false);
  const [selected5, setSelected5] = useState(false);

  return (

    <div>
      <div className="Store_info_wrap">
       <div className="logo">
        <img src={Logo} alt="" />
      </div>
      <div className="text">
        <img src={Text} alt="" />
      </div>
      <div className="input">
      <div className="input_name">
        <p>가게명</p>
        <input type="text" name="name" placeholder="가게명을 입력해주세요." required />
      </div>
      <div className="input_adderess">
        <p>가게 주소</p>
        <input type="text" name="adderess" placeholder="가게 주소를 입력해주세요." required />
      </div>
      <div className="input_info">
        <p>한 줄 설명</p>
        <input type="text" name="info" placeholder="회원님의 가게를 간단히 소개해주세요." required />
      </div>
      <div className="input_detail">
        <p>상세설명</p>
        <input type="text" name="detail" placeholder="회원님의 가게를 상세히 소개해주세요." required />
      </div>
      </div>

      <div className="table">
        <p>테이블 수</p>
        <div className="table_count">
            <div>
              <button onClick={() => count > 1 && setCount(count - 1)}>-</button>
              <span>{count}</span>
              <button onClick={() => setCount(count + 1)}>+</button>
              </div></div>
      </div>

      <div className="detail_box">
        <p>가게 특성</p>
        <div className="detail_top">
        <button className={`detail_1 ${selected1 ? 'selected' : ''}`}
            onClick={() => setSelected1(!selected1)}>
           <p>매운맛 조절 가능</p>
           </button>
          <button className={`detail_2 ${selected2 ? 'selected' : ''}`}
            onClick={() => setSelected2(!selected2)}>
            <p>비건 변경 가능</p>
          </button></div>
          <div className="detail_bottom">
          <button className={`detail_3 ${selected3 ? 'selected' : ''}`}
            onClick={() => setSelected3(!selected3)}>
            <p>Takeout 가능</p>
          </button>
          <button className={`detail_4 ${selected4 ? 'selected' : ''}`}
            onClick={() => setSelected4(!selected4)}>
            <p>반려견 동반 가능</p>
          </button>
          <button className={`detail_5 ${selected5 ? 'selected' : ''}`}
            onClick={() => setSelected5(!selected5)}>
            <p>직접 추가하기</p>
          </button></div>
      </div>

      <div className="start_button">
        <button onClick={() => navigate('/owner_home_first')}>
        <img src={Start} alt="" />
        </button>
      </div>

      </div>
     </div>
  )
}

export default Store_info
