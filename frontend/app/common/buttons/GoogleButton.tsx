import React from 'react'
import { D2CodingBold } from '@/app/fonts'

interface Props {
    theme?: 'light' | 'dark'
    children: React.ReactNode
    className?: string
    onClick?: () => void
}

export function GoogleButton({ theme = 'light', children, className, onClick }: Props) {
    return (
        <button
            style={{
                color: theme === 'light' ? '#000' : '#fff',
                border: theme === 'light' ? '1px solid #d1d5db' : 'none'
            }}
            className={`
                xl:text-[16px]
                ${
                    D2CodingBold.className
                } w-full flex gap-4 justify-center items-center mb-4 p-4 rounded-[100px] text-[14px] select-none shadow-sm 
                ${theme === 'light' ? 'bg-white' : 'bg-gray-900'}
                ${className}
            `}
            onClick={onClick}
        >
            <img className="w-[18px] h-[18px] object-cover" src="/images/googleIcon.webp" alt="" />
            {children}
        </button>
    )
}
