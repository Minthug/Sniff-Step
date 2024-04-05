import React from 'react'
import { D2CodingBold } from '../fonts'

interface Props {
    theme?: 'light' | 'dark'
    children: React.ReactNode
    className?: string
}

export function GoogleLogin({ theme = 'light', children, className }: Props) {
    return (
        <button
            style={{
                backgroundColor: theme === 'light' ? '#fff' : '#000',
                color: theme === 'light' ? '#000' : '#fff',
                border: theme === 'light' ? '1px solid #d1d5db' : 'none'
            }}
            className={`
                ${D2CodingBold.className} w-full flex gap-4 justify-center items-center mb-4 p-4 rounded-[100px] select-none shadow-sm
                ${className}
            `}
        >
            <img className="w-[18px] h-[18px] object-cover" src="/googleIcon.webp" alt="" />
            {children}
        </button>
    )
}
