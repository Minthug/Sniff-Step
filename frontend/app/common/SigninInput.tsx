import React from 'react'

interface Props {
    placeholder: string
    type: 'text' | 'password'
    onChange: (value: string) => void
}

export default function SigninInput({ placeholder, type, onChange }: Props) {
    return (
        <input
            className="w-full p-4 outline-none border border-gray-300 rounded-2xl text-[14px]"
            placeholder={placeholder}
            type={type}
            onChange={(e) => onChange(e.target.value)}
        />
    )
}
