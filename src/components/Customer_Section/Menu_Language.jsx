import React from 'react'
import qr from '../../assets/img/cus_menu/qr.svg'
import language from '../../assets/img/cus_menu/language.svg'
import main from '../../assets/img/cus_menu/main.png'
import header from '../../assets/img/cus_menu/header.png'
import cart from '../../assets/img/cus_menu/cart.svg'
import { Link } from 'react-router-dom'
import NumberSelector from './Menu_Table'

const Menu_language = () => {
  return (
    <div id="Menu_Language_Wrap" className="container">
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
                    <div className="tag">
                        <Link to='/menu_best'>
                            베스트 메뉴
                        </Link>
                    </div>
                    <div id='select_tag' className="tag">
                        <Link to='/menu_language'>
                            언어 기반 메뉴 추천
                        </Link>
                    </div>
                    <div className="tag">비건 메뉴</div>
                    <div className="tag">맵지 않은 메뉴</div>
                </div>
                <div className="menu_list">
                    <div className="recommend">
                        <div className="num">No. 1</div>
                    <div className="recommend_text">일본인 손님들에겐 가장 인기인 메뉴에요!</div>
                    </div>
                    <div id='menu' className="menu1">
                    <div className="text">
                        <h1>소고기 미역국 정식</h1>
                        <p>부드러운 소고기와 진한 국물의 
                        <br />미역국 + 밥 + 반찬 3종. 맵지 않아요!
                        </p>
                    </div>
                    <div className="order">
                        <button className="order_btn">
                        주문
                        </button>
                        <div className="order_count">
                        <button className="count_minus">-</button>
                        1
                        <button className="count_plus">+</button>
                        </div>
                    </div>
                </div>
                <div className="recommend">
                        <div className="num">No. 2</div>
                    <div className="recommend_text"></div>
                </div>
                <div id='menu' className="menu2">
                    <div className="text">
                        <h1>제육볶음 덮밥</h1>
                        <p>매콤달콤하게 볶은 돼지고기 제육에 고
                        <br />소한 계란후라이까지.
                        </p>
                    </div>
                    <div className="order">
                        <button className="order_btn">
                        주문
                        </button>
                        <div className="order_count">
                        <button className="count_minus">-</button>
                        1
                        <button className="count_plus">+</button>
                        </div>
                    </div>
                </div>
                 <div className="recommend">
                        <div className="num">No. 2</div>
                    <div className="recommend_text"></div>
                </div>
                <div id='menu' className="menu3">
                    <div className="text">
                        <h1>들기름 비빔밥</h1>
                        <p>나물 5종과 밥, 들기름, 간ㅇ으로 비벼먹
                        <br />는 한국식 채식 비빔밥.
                        </p>
                    </div>
                    <div className="order">
                        <button className="order_btn">
                        주문
                        </button>
                        <div className="order_count">
                        <button className="count_minus">-</button>
                        1
                        <button className="count_plus">+</button>
                        </div>
                    </div>
                </div>
                </div>
                </div>
            </main>
            <div className="icon">
                <div id="icon" className="cart_icon">
                    <div className="cart">
                    <img src={cart} alt="" />
                    <div className="cart_count">1</div>
                </div>
                </div>
            <div id="icon" className="table_icon">
                <NumberSelector/>
            </div>
        </div>
    </div>
  )
}

export default Menu_language