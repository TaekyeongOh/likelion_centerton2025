import React, { useState, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import back_btn from '../../assets/img/cus_order/back_btn.svg'
import qr_btn from '../../assets/img/cus_order/qr_btn.svg'
import trans_btn from '../../assets/img/cus_order/trans_btn.svg'

const Cus_order = () => {
  const navigate = useNavigate();

  // 모달 상태들
  const [isCardOpen, setIsCardOpen] = useState(false);
  const [isDoneOpen, setIsDoneOpen] = useState(false);
  const [isRatingOpen, setIsRatingOpen] = useState(false); 
  const [rating, setRating] = useState(0);

  const goToOptions = () => navigate('/cus_options');

  // 스크롤 락: 셋 중 하나라도 열려있으면 body 스크롤 잠금
  useEffect(() => {
    const lock = isCardOpen || isDoneOpen || isRatingOpen;
    document.body.style.overflow = lock ? 'hidden' : '';
    return () => { document.body.style.overflow = ''; };
  }, [isCardOpen, isDoneOpen, isRatingOpen]);

  const openCard = () => setIsCardOpen(true);
  const closeCard = () => setIsCardOpen(false);

  // 주문완료 → 별점 모달로 전환
  const openRating = () => {
    setIsDoneOpen(false);
    setIsRatingOpen(true);
  };

  // 주문하기 → 주문완료 모달
  const openDone = () => setIsDoneOpen(true);
  const closeDone = () => setIsDoneOpen(false);

  // 별 클릭
  const selectStar = (n) => setRating(n);

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
            <p>부드러운 소고기와 진한 국물의<br />미역국 + 밥 + 반찬 3종. 맵지 않아요!</p>
          </div>
          <div className="menu_right">1개</div>
        </div>

        <div className="menu_sec">
          <div className="menu_left">
            <span className="title">소고기 미역국 정식</span>
            <p>부드러운 소고기와 진한 국물의<br />미역국 + 밥 + 반찬 3종. 맵지 않아요!</p>
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

        {/* 주문카드 2개 → 카드 시트 */}
        <div
          className="total_2"
          role="button"
          tabIndex={0}
          onClick={openCard}
          onKeyDown={(e) => (e.key === 'Enter' || e.key === ' ') && openCard()}
        >
          <span className="how">주문카드 </span>
          <span className="price">2개</span>
        </div>

        {/* 주문하기 → 주문완료 모달 */}
        <div
          className="go_order"
          role="button"
          tabIndex={0}
          onClick={openDone}
          onKeyDown={(e) => (e.key === 'Enter' || e.key === ' ') && openDone()}
        >
          주문하기
        </div>
      </div>

      {/* 주문카드 시트 */}
      {isCardOpen && (
        <div className="card_overlay" onClick={closeCard} aria-modal="true" role="dialog">
          <div className="card_sheet" onClick={(e) => e.stopPropagation()}>
            <h2>이 주문 카드를 추가했어요.</h2>

            <div className="card_list">
              <div className="card_item">
                <div className="icon" aria-hidden>🌿</div>
                <p>비건 변경을 원해요</p>
              </div>
              <div className="card_item">
                <div className="icon" aria-hidden>🧄</div>
                <p>마늘 빼주세요</p>
              </div>
            </div>

            <button className="confirm_btn" onClick={closeCard}>확인했어요</button>
          </div>
        </div>
      )}

      {/* 주문완료 모달: 어디든 클릭 → 별점 모달 */}
      {isDoneOpen && (
        <div className="card_overlay" onClick={openRating} aria-modal="true" role="dialog">
          <div className="done_modal" onClick={openRating}>
            <div className="illus" aria-hidden>🧾🥤</div>
            <h3>주문 완료!</h3>
            <p>곧 맛있는 식사가 준비될 거예요.</p>
            <button className="confirm_btn">확인</button>
          </div>
        </div>
      )}

      {/* 별점 모달 */}
      {isRatingOpen && (
        <div
          className="card_overlay"
          aria-modal="true"
          role="dialog"
          onClick={() => setIsRatingOpen(false)}
        >
          <div className="rating_modal" onClick={(e) => e.stopPropagation()}>
            <h3>이번 주문은 어땠나요?</h3>

            <div className="stars" role="group" aria-label="별점 선택">
              {[1, 2, 3, 4, 5].map((n) => (
                <button
                  key={n}
                  type="button"
                  className={`star ${rating >= n ? 'filled' : ''}`}
                  aria-label={`${n}점`}
                  onClick={() => selectStar(n)}
                >
                  <svg viewBox="0 0 24 24" width="28" height="28" aria-hidden="true">
                    <path
                      className="star-shape"
                      d="M12 2.5l2.9 6 6.6.9-4.8 4.7 1.2 6.6L12 17.9 6.1 20.7l1.2-6.6L2.5 9.4l6.6-.9L12 2.5z"
                    />
                  </svg>
                </button>
              ))}
            </div>

            <p className="hint">주문 만족도를 남겨주세요.</p>

            <button
              className={`confirm_btn rate_btn ${rating > 0 ? 'active' : ''}`}
              disabled={rating === 0}
              onClick={() => setIsRatingOpen(false)}
            >
              평가 완료
            </button>
          </div>
        </div>
      )}
    </div>
  );
};

export default Cus_order
