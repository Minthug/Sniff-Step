'use client'

import React, { useRef } from 'react'
import { LocaleHome } from '@/app/types/locales'
import { container } from '@/app/common'
import { useIntersectionObserver } from '@/app/hooks'

interface Props {
    text: LocaleHome
}

export function MobileSecondSection({ text }: Props) {
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
                    <div className="pb-[40px] text-[#10b94e] text-[32px] font-[700]">{text.section2.title}</div>
                    <div className="text-[28px]">{text.section2.catchPhrase1}</div>
                    <div className="text-[28px]">{text.section2.catchPhrase2}</div>
                </div>
                <img
                    ref={target2}
                    className="relative max-w-[500px] w-full mb-[40px] z-10 transition-opacity duration-[1s] opacity-0"
                    src="/images/main/mobile-1-section.png"
                    alt=""
                />
                <div
                    ref={target3}
                    className="w-[320px] h-full flex flex-col items-center text-center text-[18px] text-black font-[600] leading-[1.6] transition-opacity duration-[1s] opacity-0 whitespace-break-spaces"
                >
                    {text.section2.description}
                </div>
            </div>
        </div>
    )
}
