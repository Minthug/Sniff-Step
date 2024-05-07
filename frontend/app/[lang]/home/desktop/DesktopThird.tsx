'use client'

import React, { useRef } from 'react'
import { LocaleHome } from '@/app/types/locales'
import { useIntersectionObserver } from '@/app/hooks'
import { MdAppRegistration, MdPhoneInTalk } from 'react-icons/md'
import { FaWpforms } from 'react-icons/fa6'

interface Props {
    text: LocaleHome
}

export function DesktopThird({ text }: Props) {
    const target = useRef<HTMLDivElement>(null)
    const target2 = useRef<HTMLDivElement>(null)
    useIntersectionObserver([target, target2])

    return (
        <div
            className={`
                xl:flex 
                max-w-[1230px] h-full items-center px-[144px] mx-auto hidden
            `}
        >
            <div className="w-full flex flex-col gap-20 py-[100px]">
                <div ref={target} className="flex flex-col gap-2 transition-opacity duration-[1s] opacity-0">
                    <div className="text-[#10b94e] font-[600] text-[32px]">{text.section3.title}</div>
                    <div>
                        <div className="text-[48px] font-[700] leading-[1.4]">{text.section3.catchPhrase1}</div>
                        <div className="text-[48px] font-[700] leading-[1.4]">{text.section3.catchPhrase2}</div>
                    </div>
                </div>
                <div ref={target2} className="flex gap-10 transition-opacity duration-[1s] opacity-0">
                    <div className="flex-1 h-[144px] flex gap-2 flex-col justify-center items-center text-[18px] font-[700] shadow-xl rounded-xl bg-white">
                        <MdAppRegistration size={32} />
                        <div className="text-center">{text.section3.box1}</div>
                    </div>
                    <div className="flex-1 h-[144px] flex gap-2 flex-col justify-center items-center text-[18px] font-[700] shadow-xl rounded-xl bg-white">
                        <MdPhoneInTalk size={32} />
                        <div className="text-center">{text.section3.box2}</div>
                    </div>
                    <div className="flex-1 h-[144px] flex gap-2 flex-col justify-center items-center text-[18px] font-[700] shadow-xl rounded-xl bg-white">
                        <FaWpforms size={32} />
                        <div className="text-center">{text.section3.box3}</div>
                    </div>
                </div>
            </div>
        </div>
    )
}
