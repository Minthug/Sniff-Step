'use client'

import React, { useRef } from 'react'
import Link from 'next/link'
import { Home, Locales } from '@/app/types/locales'
import { container } from '@/app/common'
import { useIntersectionObserver } from '@/app/hooks'

interface Props {
    lang: Locales
    text: Home
}

export function MobileFourthSection({ lang, text }: Props) {
    const target = useRef<HTMLDivElement>(null)
    useIntersectionObserver([target], 1)

    return (
        <div
            ref={target}
            className={`${container.home.mobile} w-full flex justify-center flex-col mt-[40px] mb-[144px] transition-opacity duration-[1s] opacity-0`}
        >
            <div className="flex justify-center text-[14px] font-[700] mt-[32px]">{text.section4.title}</div>
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
