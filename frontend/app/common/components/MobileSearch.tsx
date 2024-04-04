import React from 'react'
import { container } from '../styles'

interface Props {
    setOnMobileSearch: (onMobileSearch: boolean) => void
}

export function MobileSearch({ setOnMobileSearch }: Props) {
    return (
        <div
            className={`
                        ${container}
                        absolute flex gap-4 justify-between left-0 w-full h-full py-2 bg-white
                `}
        >
            <input
                className="w-full pl-4 pr-[40px] py-2 text-[14px] text-[#898989] bg-[#F2F3F6] rounded-[4px] outline-none"
                onChange={() => {}}
                type="text"
                placeholder="동네 검색으로 산책인을 찾아보세요"
            />
            <button
                className={`
                            hover:bg-slate-100 active:bg-slate-200
                            w-[50px] h-[40px] font-bold rounded-s
                    `}
                onClick={() => setOnMobileSearch(false)}
            >
                취소
            </button>
        </div>
    )
}
