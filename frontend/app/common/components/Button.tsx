'use client'
import { D2CodingLight } from '@/app/fonts'
import { useRouter } from 'next/navigation'
import React from 'react'

interface Props {
    contents: string
    href: string
    className?: string
}

export default function Button({ contents, href, className }: Props) {
    const router = useRouter()
    return (
        <button
            className={`
                        ${D2CodingLight.className}
                        [&>div]:hover:w-full
                        relative py-2 text-[14px] tracking-[-0.12rem] font-[700]
                        ${className}
            `}
            onClick={() => router.push(href)}
        >
            {contents}
            <div
                className={`
                            w-0 h-[2px] bg-[#10b94e] rounded-xl transition-all duration-300
                        `}
            />
        </button>
    )
}
