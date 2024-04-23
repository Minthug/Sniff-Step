import React from 'react'

interface Props {
    children: React.ReactNode
    className?: string
}

export function SeparateLine({ children, className }: Props) {
    return (
        <div className={`flex gap-2 items-center mb-2 text-[14px] ${className}`}>
            <div className="w-full h-[1px] bg-gray-300" />
            <div className="min-w-fit text-center text-gray-400">{children}</div>
            <div className="w-full h-[1px] bg-gray-300" />
        </div>
    )
}
