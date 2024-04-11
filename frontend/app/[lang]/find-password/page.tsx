'use client'

import React from 'react'
import Link from 'next/link'
import { Locales } from '@/app/types/locales'
import { D2CodingBold } from '@/app/fonts'
import { FaLongArrowAltLeft } from 'react-icons/fa'

interface Props {
    params: { lang: Locales }
}

export default function page({ params: { lang } }: Props) {
    return (
        <div className="relative w-full h-screen flex">
            <div className="w-[27.5%] min-w-[400px] h-full">
                <Link
                    className="absolute left-8 h-[60px] flex gap-2 justify-center items-center animate-leftBounce"
                    href={`/${lang}/signin`}
                >
                    <FaLongArrowAltLeft className="w-[20px] h-[20px]" />
                    <div className={`${D2CodingBold.className}`}>Login</div>
                </Link>
                <img
                    className="w-full h-full object-contain select-none bg-[#C9E2EB]"
                    src="https://cdn.dribbble.com/users/338126/screenshots/15483287/media/2f03c8290d612078b76883e579d4fd99.gif"
                />
            </div>
            <div className="pl-[160px] w-[720px]"></div>
        </div>
    )
}
