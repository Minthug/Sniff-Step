'use client'

import React, { useRef } from 'react'
import { LocaleHome, Locales } from '@/app/types/locales'
import { useIntersectionObserver } from '@/app/hooks'
import Link from 'next/link'

interface Props {
    lang: Locales
    text: LocaleHome
}

export function DesktopFourth({ lang, text }: Props) {
    const target = useRef<HTMLDivElement>(null)
    useIntersectionObserver([target], 1)

    return (
        <div
            ref={target}
            className={`
                xl:flex 
                max-w-[1230px] mx-auto hidden w-full justify-center flex-col mb-[144px] px-[144px] transition-opacity duration-[1s] opacity-0
            `}
        >
            <div className="flex justify-center text-[28px] font-[700] mt-[32px] mb-[32px]">{text.section4.title}</div>
            <Link
                href={`/${lang}/boards`}
                className={`
                            hover:bg-green-600 hover:text-gray-100
                            active:bg-green-700
                            2xl:text-[20px]
                            py-4 mt-4 bg-[#10b94e] rounded-lg font-[500] text-[18px] text-center text-white
                        `}
            >
                {text.Link}
            </Link>
        </div>
    )
}
