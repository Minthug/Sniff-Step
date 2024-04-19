import React, { useRef } from 'react'
import { container } from '@/app/common'
import { D2CodingBold } from '@/app/fonts'
import { useIntersectionObserver } from '@/app/hooks'

export default function FourthSection() {
    const target = useRef<HTMLDivElement>(null)

    useIntersectionObserver([target], 1)

    return (
        <div
            ref={target}
            className={`${container.home.desktop.section} w-full flex justify-center flex-col mb-[144px] transition-opacity duration-[1s] opacity-0`}
        >
            <div className="flex justify-center text-[28px] font-[700] mt-[32px] mb-[32px]">지금 바로 스니프 앤 스텝을 이용해보세요!</div>
            <button
                className={`
                                ${D2CodingBold.className}
                                hover:bg-green-600 hover:text-gray-100
                                active:bg-green-700
                                2xl:text-[32px]
                                py-4 mt-4 bg-[#10b94e] rounded-lg font-[500] text-[24px] text-white
                        `}
            >
                산책인 공고 구경하기
            </button>
        </div>
    )
}
