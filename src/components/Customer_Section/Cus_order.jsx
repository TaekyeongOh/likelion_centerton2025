import React from 'react'
import { useNavigate } from 'react-router-dom'
import back_btn from '../../assets/img/cus_order/back_btn.svg'
import qr_btn from '../../assets/img/cus_order/qr_btn.svg'
import trans_btn from '../../assets/img/cus_order/trans_btn.svg'

const Cus_order = () => {
    const navigate = useNavigate();

    const goToOptions = () => {
        navigate('/cus_options'); 
    };
    return (
        <div className='cusorder_wrap container'>
            <div className="header">
                <button className="back_btn"><img src={back_btn} alt="" /></button>
                <div className="right_btns">
                    <button className="qr"><img src={qr_btn} alt="" /></button>
                    <button className="trans"><img src={trans_btn} alt="" /></button>
                </div>
            </div>
            <div className="co_main">
                <h1>ORDER</h1>
                <div className="menu_sec">
                    <div className="menu_left">
                        <span className="title">소고기 미역국 정식</span>
                        <p>부드러운 소고기와 진한 국물의<br />
                            미역국 + 밥 + 반찬 3종. 맵지 않아요!</p>
                    </div>
                    <div className="menu_right">1개</div>
                </div>
                <div className="menu_sec">
                    <div className="menu_left">
                        <span className="title">소고기 미역국 정식</span>
                        <p>부드러운 소고기와 진한 국물의<br />
                            미역국 + 밥 + 반찬 3종. 맵지 않아요!</p>
                    </div>
                    <div className="menu_right">1개</div>
                </div>
                <button className="add_order">주문 추가하기</button>
                <div className="add_ordercard">
                    <div className="menu_left">
                        <span className="title">주문카드 추가하기</span>
                        <p>나의 입맛과 식성에 맞게 추가 주문 사항을 카드로 넣어봐요.</p>
                    </div>
                    <button className="amenu_right" onClick={goToOptions}>추가</button>
                </div>
                <div className="total">
                    <span className="how">총 가격</span>
                    <span className="price">33,000</span>
                </div>
                <div className="total_2">
                    <span className="how">주문카드 </span>
                    <span className="price">2개</span>
                </div>
                <div className="go_order">주문하기</div>
            </div>

        </div>
    )
}

export default Cus_order
