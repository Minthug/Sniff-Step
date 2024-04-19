import React, { useRef } from 'react'
import { container } from '@/app/common'
import { MdAppRegistration, MdPhoneInTalk } from 'react-icons/md'
import { FaWpforms } from 'react-icons/fa6'
import { useIntersectionObserver } from '@/app/hooks'

export function MobileThirdSection() {
    const target = useRef<HTMLDivElement>(null)
    const target2 = useRef<HTMLDivElement>(null)
    useIntersectionObserver([target, target2])

    return (
        <div className={`${container.home.mobile} h-full flex items-center mt-[80px]`}>
            <div className="w-full flex flex-col gap-[40px]">
                <div ref={target} className="flex flex-col items-center text-center gap-2 transition-opacity duration-[1s] opacity-0">
                    <div className="text-[28px] font-[600] leading-[1.4]">
                        <div className="pb-[40px] text-[#10b94e] text-[32px] font-[700]">이용방법</div>
                        <div>간단한 게시물 등록</div>
                        <div>자유로운 소통</div>
                    </div>
                </div>
                <div ref={target2} className="flex flex-col gap-10 transition-opacity duration-[1s] opacity-0">
                    <div className="h-[144px] flex gap-2 flex-col justify-center items-center text-[24px] font-[700] shadow-md rounded-xl bg-white">
                        <MdAppRegistration />
                        <div>산책인 등록</div>
                    </div>
                    <div className="h-[144px] flex gap-2 flex-col justify-center items-center text-[24px] font-[700] shadow-md rounded-xl bg-white">
                        <MdPhoneInTalk />
                        <div>소통 방식 및 시간 설정</div>
                    </div>
                    <div className="h-[144px] flex gap-2 flex-col justify-center items-center text-[24px] font-[700] shadow-md rounded-xl bg-white">
                        <FaWpforms />
                        <div>자유로운 형식</div>
                    </div>
                </div>
            </div>
        </div>
    )
}
