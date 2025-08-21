import React, { useState, useEffect } from 'react'
import qr from '../../assets/img/cus_menu/qr.svg'
import language from '../../assets/img/cus_menu/language.svg'
import main from '../../assets/img/cus_menu/main.png'
import header from '../../assets/img/cus_menu/header.png'
import cart from '../../assets/img/cus_menu/cart.svg'
import { Link } from 'react-router-dom'
import NumberSelector from './Menu_Table'

const Menu = () => {
    // API로부터 받을 상태 변수들
    const [menus, setMenus] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    // 메뉴 카운트 상태
    const [counts, setCounts] = useState({});

    const handlePlus = (menuName) => {
        setCounts(prev => ({ ...prev, [menuName]: (prev[menuName] || 0) + 1 }));
    };
    const handleMinus = (menuName) => {
        setCounts(prev => ({ ...prev, [menuName]: Math.max(0, (prev[menuName] || 0) - 1) }));
    };

    useEffect(() => {
        const userId = '1';

        const fetchAllData = async () => {
            try {
                setLoading(true);
                const response = await fetch(`/api/store/${userId}/all`);
                if (!response.ok) {
                    throw new Error('API 호출에 실패했습니다.');
                }
                const data = await response.json(); // 실제 데이터: { menus: [...] }

                // 1. 실제 데이터 구조에 맞게 메뉴 상태만 업데이트
                if (data && Array.isArray(data.menus)) {
                    setMenus(data.menus); // 필터링 없이 바로 배열을 사용
                } else {
                    setMenus([]);
                }
            } catch (err) {
                setError(err);
            } finally {
                setLoading(false);
            }
        };

        fetchAllData();
    }, []);

    if (loading) return <div>로딩 중...</div>;
    if (error) return <div>에러 발생: {error.message}</div>;

    return (
        <div id="Menu_wrap" className="container">
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
                    <div className="store">RESTAURANT</div>
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
                      <div id='select_tag' className="tag"><Link to='/'>전체 메뉴</Link></div>
                      <div className="tag"><Link to='/menu_best'>베스트 메뉴</Link></div>
                      <div className="tag"><Link to='/menu_language'>언어 기반 메뉴 추천</Link></div>
                      <div className="tag">비건 메뉴</div>
                      <div className="tag">맵지 않은 메뉴</div>
                  </div>
                  <div className="menu_list">
                        {menus.map((menu, idx) => (
                            <div key={menu.menuName || idx} className={`menu_card menu${idx + 1}`}>
                                <div className="text">
                                    <h1>{menu.menuName}</h1> 
                                    <p>{menu.shortDescription}</p>
                                </div>
                                <div className="order">
                                    <button className="order_btn">주문</button>
                                    <div className="order_count">
                                        <button className="count_minus" onClick={() => handleMinus(menu.menuName)}>-</button>
                                        <div className="count">{counts[menu.menuName] || 0}</div>
                                        <button className="count_plus" onClick={() => handlePlus(menu.menuName)}>+</button>
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
    );
}

export default Menu;