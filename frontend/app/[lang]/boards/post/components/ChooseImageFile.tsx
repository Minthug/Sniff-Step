import React from 'react'
import { FaPlus } from 'react-icons/fa6'

interface Props {
    file: File | null
    handleFileChange: (e: React.ChangeEvent<HTMLInputElement>) => void
}

export function ChooseImageFile({ file, handleFileChange }: Props) {
    return (
        <button
            className="relative w-full h-[361px] flex justify-center items-center mb-8 bg-[#d9d9d9] rounded-md cursor-pointer"
            onClick={() => document.getElementById('file')?.click()}
        >
            {file && <img className="absolute w-full h-full object-contain bg-white" src={URL.createObjectURL(file)} alt="images" />}
            <input type="file" id="file" accept="image/png, image/gif, image/jpeg" onChange={handleFileChange} className="hidden" />
            <FaPlus className="text-[#fff] text-[128px]" />
        </button>
    )
}
