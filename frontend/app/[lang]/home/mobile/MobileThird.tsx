'use client'

import React, { useRef } from 'react'
import { LocaleHome } from '@/app/types/locales'
import { useIntersectionObserver } from '@/app/hooks'
import { MdAppRegistration, MdPhoneInTalk } from 'react-icons/md'
import { FaWpforms } from 'react-icons/fa6'

interface Props {
    text: LocaleHome
}

export function MobileThird({ text }: Props) {
    const target = useRef<HTMLDivElement>(null)
    const target2 = useRef<HTMLDivElement>(null)
    useIntersectionObserver([target, target2])

    return (
        <div
            className={`
                xl:hidden 
                h-full flex items-center mt-[80px] px-[20px]
            `}
        >
            <div className="w-full flex flex-col gap-[40px]">
                <div ref={target} className="flex flex-col items-center text-center gap-2 transition-opacity duration-[1s] opacity-0">
                    <div className="text-[28px] font-[600] leading-[1.4]">
                        <div className="pb-[40px] text-[#10b94e] text-[32px] font-[700]">{text.section3.title}</div>
                        <div>{text.section3.catchPhrase1}</div>
                        <div>{text.section3.catchPhrase2}</div>
                    </div>
                </div>
                <div ref={target2} className="flex flex-col gap-10 transition-opacity duration-[1s] opacity-0">
                    <div className="h-[144px] flex gap-2 flex-col justify-center items-center text-[18px] font-[700] shadow-md rounded-xl bg-white">
                        <MdAppRegistration size={32} />
                        <div>{text.section3.box1}</div>
                    </div>
                    <div className="h-[144px] flex gap-2 flex-col justify-center items-center text-[18px] font-[700] shadow-md rounded-xl bg-white">
                        <MdPhoneInTalk size={32} />
                        <div>{text.section3.box2}</div>
                    </div>
                    <div className="h-[144px] flex gap-2 flex-col justify-center items-center text-[18px] font-[700] shadow-md rounded-xl bg-white">
                        <FaWpforms size={32} />
                        <div>{text.section3.box3}</div>
                    </div>
                </div>
            </div>
        </div>
    )
}
