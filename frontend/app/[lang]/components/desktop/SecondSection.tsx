import React from 'react'
import { container } from '@/app/common'

export function SecondSection() {
    return (
        <div className={`${container.home.desktop.section} relative h-full flex`}>
            <div className="flex-1 flex flex-col">
                <div className="relative w-fit flex mx-auto">
                    <img className="relative w-[500px] z-10" src="/images/main/iPhone_clay_shadow.png" alt="" />
                    <img
                        className="absolute top-[88px] left-1/2 -translate-x-1/2 w-[271px] h-[606px]"
                        src="/images/main/2-section.png"
                        alt=""
                    />
                </div>
                <div className="w-[320px] h-full flex flex-col mx-auto mt-[40px] text-[22px] text-[#212632] font-[600] leading-[1.6]">
                    등록된 산책 대행인들을 만나보세요! <br /> 당신의 반려견을 위한 산책인 <br /> 커뮤니티를 만들어 볼 수 있어요.
                </div>
            </div>
            <div className="flex-1 flex flex-col">
                <div className="w-[500px] h-full flex flex-col mx-auto mb-[40px] font-[700] leading-[1.4]">
                    <div className="py-[20px] mt-[40px] text-[#10b94e] text-[32px] font-[600]">산책인 등록</div>
                    <div className="text-[48px]">더 가깝게,</div>
                    <div className="text-[48px]">우리 동네 산책대행</div>
                </div>
                <div className="relative w-fit flex mx-auto">
                    <img className="relative w-[500px] z-10" src="/images/main/iPhone_clay_shadow.png" alt="" />
                    <img
                        className="absolute top-[88px] left-1/2 -translate-x-1/2 w-[290px] h-[606px]"
                        src="/images/main/4-section.png"
                        alt=""
                    />
                </div>
            </div>
        </div>
    )
}
