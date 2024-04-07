import React from 'react'
import { D2CodingBold } from '../fonts'

interface Props {
    theme?: 'light' | 'dark'
    children: React.ReactNode
    className?: string
    onClick: () => void
}

export default function SigninButton({ theme = 'light', children, className, onClick }: Props) {
    return (
        <button
            style={{
                color: theme === 'light' ? '#000' : '#fff',
                border: theme === 'light' ? '1px solid #d1d5db' : 'none'
            }}
            className={`${D2CodingBold.className}
                    ${theme === 'light' ? 'bg-white' : 'bg-gray-900'}
                    w-full flex gap-4 justify-center items-center mb-4 p-4 rounded-[100px] select-none shadow-sm
                    ${className}
                `}
            onClick={onClick}
        >
            {children}
        </button>
    )
}
