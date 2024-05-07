import React from 'react'

interface Props {
    value: string
    placeholder: string
    type: 'text' | 'password'
    className?: string
    onChange: (e: React.ChangeEvent<HTMLInputElement>) => void
}

export function Input({ value, placeholder, type, className, onChange }: Props) {
    return (
        <input
            type={type}
            value={value}
            onChange={onChange}
            className={`
                xl:placeholder:text-[14px]
                placeholder:text-[12px]
                w-full p-4 outline-none border border-gray-300 rounded-lg text-[14px] ${className}
            `}
            placeholder={placeholder}
        />
    )
}
