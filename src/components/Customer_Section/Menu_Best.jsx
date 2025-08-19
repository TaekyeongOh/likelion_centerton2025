import React, { useState , useEffect } from 'react'
import qr from '../../assets/img/cus_menu/qr.svg'
import language from '../../assets/img/cus_menu/language.svg'
import main from '../../assets/img/cus_menu/main.png'
import header from '../../assets/img/cus_menu/header.png'
import cart from '../../assets/img/cus_menu/cart.svg'
import { Link } from 'react-router-dom'
import NumberSelector from './Menu_Table'

const Menu_Best = () => {

     //메뉴 카운트
    const [counts, setCounts] = useState([0,0,0,0,0]);
    
    const handlePlus = (idx) => {
        setCounts(prev => prev.map((val, i) => (i === idx ? val + 1 : val)));
      };
    const handleMinus = (idx) => {
        setCounts(prev => prev.map((val, i) => (i === idx ? (val > 0 ? val - 1 : 0) : val)));
      };

    const [menus, setMenus] = useState([]); // API 결과 저장
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);


  useEffect(() => {
    const userId = '1';

    fetch(`/api/store/${userId}/top3`)
      .then(response => {
        // 서버에서 4xx, 5xx 에러 응답을 받았을 때를 대비한 최소한의 방어 코드
        if (!response.ok) {
          throw new Error('API 호출에 실패했습니다.');
        }
        return response.json();
      })
      .then(data => {
        // data.topMenus가 존재하고 배열일 경우에만 상태 업데이트
        if (data && Array.isArray(data.topMenus)) {
          setMenus(data.topMenus);
        }
      })
      .catch(error => {
        console.error('데이터를 가져오는 데 실패했습니다:', error);
        setError(error); 
      });
  }, []);

  return (
    <div id="Menu_Best_Wrap" className="container">
        <img src={main} alt="" className="main" />
        <header>
            <div className="header_icon">
                <img src={qr} arlt="" />
                <img src={language} alt="" />
            </div>
            <div className="header">
                <img src={header} alt="" />
            </div>
        </header>
        <main>
            <div className="store_info">
            <div className="store">
                RESTAURANT
            </div>
            <h1>한그릇</h1>
            <p>한국의 정을 담은 따듯한 한 끼</p>
            <div className="text">
                “한그릇”은 계절마다 바뀌는 따끈한 국물 요리와 밥
                <br />한 그릇을 정성스럽게 차려내는 따뜻한 동네 식당입니다.
            </div>
            <div className="map">
                서울 서대문구 홍제5동 하나빌딩 1층
            </div>  
            <div className="tags">
                <div className="tag">매운맛 조절 가능</div>
                <div className="tag">비건 변경 가능</div>
            </div>
            </div>
            <div className="menu">
            <h1 className='menu_text'>메뉴 보기</h1>
            <div className="tags">
                <div className="tag">
                    <Link to='/'>
                        전체 메뉴
                    </Link>
                </div>
                <div id='select_tag' className="tag">
                    <Link to='/menu_best'>
                        베스트 메뉴
                    </Link>
                </div>
                <div className="tag">
                    <Link to='/menu_language'>
                        언어 기반 메뉴 추천
                    </Link>
                </div>
                <div className="tag">비건 메뉴</div>
                <div className="tag">맵지 않은 메뉴</div>
            </div>
            <div className="menu_list">
            {menus.map((menu, idx) => (
             <div>
               <div className="num">No. {idx + 1}</div>
              <div key={idx} className={`menu_card menu${idx + 1}`}>
                <div className="text">
                  <h1>{menu.menuName}</h1>
                  <p>{menu.shortDescription}</p>
                </div>
                <div className="order">
                  <button className="order_btn">주문</button>
                  <div className="order_count">
                    <button className="count_minus" onClick={() => handleMinus(idx)}>-</button>
                    <div className="count">{counts[idx]}</div>
                    <button className="count_plus" onClick={() => handlePlus(idx)}>+</button>
                  </div>
                </div>
              </div>
              </div>
            ))}
          </div>
            </div>
        </main>
        <div className="icon">
               <Link to='/cus_order'>
                    <div id="icon" className="cart_icon">
                        <div className="cart">
                            <img src={cart} alt="" />
                        <div className="cart_count">1</div>
                        </div>
                    </div>
                </Link>
            <div id="icon" className="table_icon">
                <NumberSelector/>
            </div>
        </div>
    </div>
  )
    
}

export default Menu_Best