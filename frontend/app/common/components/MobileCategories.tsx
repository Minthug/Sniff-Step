import { D2CodingBold } from '@/app/fonts'
import React, { HTMLAttributes } from 'react'

interface Props {
    onMobileMenu: boolean
    setOnMobileMenu: (onMobileMenu: boolean) => void
}

export function MobileCategories({ onMobileMenu, setOnMobileMenu }: Props) {
    return (
        <>
            <div
                onClick={() => setOnMobileMenu(!onMobileMenu)}
                style={{
                    height: onMobileMenu ? '100%' : 0,
                    minHeight: onMobileMenu ? 'calc(100vh - 56px)' : 0
                }}
                className={`absolute left-0 top-[56px] h-[100%] w-full bg-[#222] opacity-[60%]`}
            ></div>
            <div
                style={{
                    height: onMobileMenu ? '161px' : '0px',
                    opacity: onMobileMenu ? 1 : 0,
                    transition: onMobileMenu ? 'height 0.3s, opacity 0.3s' : 'height 0.5s',
                    padding: onMobileMenu ? '16px 0' : '0'
                }}
                className={`
                    ${D2CodingBold.className}
                    absolute left-0 top-[56px] w-full items-center bg-[#fff] text-[18px]
                `}
            >
                {onMobileMenu && (
                    <>
                        <MobileCategory onClick={() => console.log('산책 시키기')}>산책 시키기</MobileCategory>
                        <MobileCategory onClick={() => console.log('산책 맡기기')}>산책 맡기기</MobileCategory>
                        <MobileCategory onClick={() => console.log('내 프로필')}>내 프로필</MobileCategory>
                    </>
                )}
            </div>
        </>
    )
}

function MobileCategory({ children, onClick }: HTMLAttributes<HTMLButtonElement>) {
    return (
        <button
            className={`
                hover:bg-slate-100 active:bg-slate-200
                w-full flex justify-center py-2 cursor-pointer
            `}
            onClick={onClick}
        >
            {children}
        </button>
    )
}
