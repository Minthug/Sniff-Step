import React from 'react'
import { Locales } from '@/app/types/locales'
import { useDaumPostcodePopup } from 'react-daum-postcode'
import { AiOutlineSearch } from 'react-icons/ai'

interface Props {
    lang: Locales
    address: string
    addressEnglish: string
    getAddress: string
    handleChangeAddress: (address: string, addressEnglish: string) => void
}

export function FindAddress({ lang, address, addressEnglish, getAddress, handleChangeAddress }: Props) {
    const openFindAddress = useDaumPostcodePopup()

    return (
        <button
            className="relative w-full max-w-[480px] h-[40px] px-4 text-[16px] text-start border border-gray-300 bg-white rounded-md overflow-hidden text-ellipsis whitespace-nowrap"
            onClick={() => {
                openFindAddress({
                    onComplete: (data) => {
                        const { address, addressEnglish } = data
                        handleChangeAddress(address, addressEnglish)
                    }
                })
            }}
        >
            {lang === 'en' && (
                <div
                    className={`
                                ${address ? 'text-black' : 'text-gray-400'}
                                w-[80%] cursor-pointer outline-none
                            `}
                >
                    {addressEnglish ? addressEnglish : address}
                </div>
            )}
            {lang === 'ko' && (
                <div
                    className={`
                                ${address ? 'text-black' : 'text-gray-400'}
                                w-[80%] cursor-pointer outline-none
                            `}
                >
                    {address ? address : getAddress}
                </div>
            )}
            <AiOutlineSearch className="absolute top-1/2 right-[4px] translate-y-[-50%] text-gray-400 text-[24px]" />
        </button>
    )
}
