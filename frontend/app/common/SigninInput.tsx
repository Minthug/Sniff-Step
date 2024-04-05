import React from 'react'

interface Props {
    value: string
    placeholder: string
    type: 'text' | 'password'
    className?: string
    onChange: (value: string) => void
}

export default function SigninInput({ value, placeholder, type, className, onChange }: Props) {
    return (
        <input
            className={`w-full p-4 outline-none border border-gray-300 rounded-2xl text-[14px] ${className}`}
            value={value}
            placeholder={placeholder}
            type={type}
            onChange={(e) => onChange(e.target.value)}
        />
    )
}
