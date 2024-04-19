import React, { useRef } from 'react'
import { container } from '@/app/common'
import { useIntersectionObserver } from '@/app/hooks'

export function MobileSecondSection() {
    const target = useRef<HTMLDivElement>(null)
    const target2 = useRef<HTMLImageElement>(null)
    const target3 = useRef<HTMLDivElement>(null)
    useIntersectionObserver([target, target2, target3])

    return (
        <div className={`${container.home.mobile} relative mt-[80px]`}>
            <div className="flex-1 flex flex-col items-center">
                <div
                    ref={target}
                    className="h-full flex flex-col mx-auto font-[600] leading-[1.4] transition-opacity duration-[1s] opacity-0 text-center"
                >
                    <div className="pb-[40px] text-[#10b94e] text-[32px] font-[700]">동네 산책인 등록</div>
                    <div className="text-[28px]">더 가깝게,</div>
                    <div className="text-[28px]">우리 동네 산책 대행인</div>
                </div>
                <img
                    ref={target2}
                    className="relative max-w-[500px] w-full mb-[40px] z-10 transition-opacity duration-[1s] opacity-0"
                    src="/images/main/mobile-1-section.png"
                    alt=""
                />
                <div
                    ref={target3}
                    className="w-[320px] h-full flex flex-col items-center text-center text-[18px] text-[#212632] font-[600] leading-[1.6] transition-opacity duration-[1s] opacity-0"
                >
                    등록된 산책 대행인들을 만나보세요! <br /> 당신의 반려견을 위한 산책인 <br /> 커뮤니티를 만들어 볼 수 있어요.
                </div>
            </div>
        </div>
    )
}
