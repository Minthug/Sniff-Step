'use client'

import React, { useRef } from 'react'
import { LocaleHome, Locales } from '@/app/types/locales'
import { useIntersectionObserver } from '@/app/hooks'
import Link from 'next/link'

interface Props {
    lang: Locales
    text: LocaleHome
}

export function MobileFourth({ lang, text }: Props) {
    const target = useRef<HTMLDivElement>(null)
    useIntersectionObserver([target], 1)

    return (
        <div
            ref={target}
            className={`
                xl:hidden 
                w-full flex justify-center flex-col mt-[40px] mb-[144px] px-[20px] transition-opacity duration-[1s] opacity-0
            `}
        >
            <div className="sm:text-[24px] flex justify-center text-[14px] font-[700] mt-[32px]">{text.section4.title}</div>
            <Link
                href={`/${lang}/boards`}
                className={`
                            hover:bg-green-600 hover:text-gray-100
                            active:bg-green-700
                            py-3 mt-4 bg-[#10b94e] rounded-lg font-[500] text-[18px] text-white text-center
                        `}
            >
                {text.Link}
            </Link>
        </div>
    )
}
