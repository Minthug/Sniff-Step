import React, { useRef } from 'react'
import { container } from '@/app/common'
import { MdAppRegistration, MdPhoneInTalk } from 'react-icons/md'
import { FaWpforms } from 'react-icons/fa6'
import { useIntersectionObserver } from '@/app/hooks'

export function ThirdSection() {
    const target = useRef<HTMLDivElement>(null)
    const target2 = useRef<HTMLDivElement>(null)
    useIntersectionObserver([target, target2])

    return (
        <div className={`${container.home.desktop} h-full flex items-center px-[144px]`}>
            <div className="w-full flex flex-col gap-20 py-[100px]">
                <div ref={target} className="flex flex-col gap-2 transition-opacity duration-[1s] opacity-0">
                    <div className="text-[#10b94e] font-[600] text-[32px]">이용방법</div>
                    <div>
                        <div className="text-[48px] font-[700] leading-[1.4]">간단한 게시물 등록</div>
                        <div className="text-[48px] font-[700] leading-[1.4]">자유로운 소통</div>
                    </div>
                </div>
                <div ref={target2} className="flex gap-10 transition-opacity duration-[1s] opacity-0">
                    <div className="flex-1 h-[144px] flex gap-2 flex-col justify-center items-center text-[24px] font-[700] shadow-xl rounded-xl bg-white">
                        <MdAppRegistration />
                        <div>산책인 등록</div>
                    </div>
                    <div className="flex-1 h-[144px] flex gap-2 flex-col justify-center items-center text-[24px] font-[700] shadow-xl rounded-xl bg-white">
                        <MdPhoneInTalk />
                        <div>소통 방식 및 시간 설정</div>
                    </div>
                    <div className="flex-1 h-[144px] flex gap-2 flex-col justify-center items-center text-[24px] font-[700] shadow-xl rounded-xl bg-white">
                        <FaWpforms />
                        <div>자유로운 형식</div>
                    </div>
                </div>
            </div>
        </div>
    )
}
