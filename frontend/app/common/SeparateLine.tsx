import React from 'react'

interface Props {
    children: React.ReactNode
}

export default function SeparateLine({ children }: Props) {
    return (
        <div className="flex gap-2 items-center mb-4">
            <div className="w-full h-[1px] bg-gray-300" />
            <div className="min-w-fit text-center text-[14px] text-gray-400">{children}</div>
            <div className="w-full h-[1px] bg-gray-300" />
        </div>
    )
}
