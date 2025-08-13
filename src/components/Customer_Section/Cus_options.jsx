import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import back_btn from '../../assets/img/cus_order/back_btn.svg'
import qr_btn from '../../assets/img/cus_order/qr_btn.svg'
import trans_btn from '../../assets/img/cus_order/trans_btn.svg'
import op01 from '../../assets/img/cus_options/op01.png'
import op02 from '../../assets/img/cus_options/op02.png'
import op03 from '../../assets/img/cus_options/op03.png'
import op04 from '../../assets/img/cus_options/op04.png'
import op05 from '../../assets/img/cus_options/op05.png'
import op06 from '../../assets/img/cus_options/op06.png'
import op07 from '../../assets/img/cus_options/op07.png'
import op08 from '../../assets/img/cus_options/op08.png'

const Cus_options = () => {
  // 어떤 옵션이 선택됐는지 저장 (index 기반)
  const [selected, setSelected] = useState(new Set())

  const options = [
    { title: ' 덜 맵게 해주세요', img: op01, iconClass: 'icon' },
    { title: ' 계란 빼주세요', img: op02, iconClass: 'icon' },
    { title: ' 간을 약하게 해주세요', img: op03, iconClass: 'icon3' },
    { title: ' 소스를 따로 주세요', img: op04, iconClass: 'icon4' },
    { title: ' 비건 변경을 원해요', img: op05, iconClass: 'icon' },
    { title: ' 마늘 빼주세요', img: op06, iconClass: 'icon' },
    { title: ' 유제품 빼주세요', img: op07, iconClass: 'icon' },
    { title: ' 밥을 많이 주세요', img: op08, iconClass: 'icon' },
    { title: ' 밥을 조금 주세요', img: op08, iconClass: 'icon' },
  ]

  const toggleOption = (idx) => {
    setSelected(prev => {
      const next = new Set(prev)
      if (next.has(idx)) next.delete(idx)
      else next.add(idx)
      return next
    })
  }

  return (
    <div className='cusoptions_wrap container'>
      <div className="header">
        <button className="back_btn"><img src={back_btn} alt="" /></button>
        <div className="right_btns">
          <button className="qr"><img src={qr_btn} alt="" /></button>
          <button className="trans"><img src={trans_btn} alt="" /></button>
        </div>
      </div>

      <div className="cp_main">
        <h1>ORDER CARD</h1>

        {options.map((opt, idx) => {
          const isSelected = selected.has(idx)
          return (
            <div
              key={idx}
              className={`opts ${isSelected ? 'selected' : ''}`}
              onClick={() => toggleOption(idx)}
              role="button"
              tabIndex={0}
              onKeyDown={(e) => { if (e.key === 'Enter' || e.key === ' ') toggleOption(idx) }}
            >
              <div className="opts_left">
                <span className='title'>{opt.title}</span>
                <div className={opt.iconClass}><img src={opt.img} alt="" /></div>
              </div>
              <div className="opts_right">
                <button className="add">
                  {isSelected ? '삭제' : '추가'}
                </button>
              </div>
            </div>
          )
        })}

        <div className="cart">
          <div className="cart_left">주문 카트</div>
          <div className="cart_right">{selected.size}개</div>
        </div>
      </div>
    </div>
  )
}

export default Cus_options
