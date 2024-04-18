'use client'

import React from 'react'
import { Locales } from '../types/locales'
import { Header, container } from '../common'
import { Footer } from '../common/footer/Footer'
import { FirstSection } from './components/desktop/FirstSection'
import { SecondSection } from './components/desktop/SecondSection'
import { MdAppRegistration, MdPhoneInTalk } from 'react-icons/md'
import { FaWpforms } from 'react-icons/fa6'
import { D2CodingBold } from '../fonts'

interface Props {
    params: { lang: Locales }
}

export default function page({ params: { lang } }: Props) {
    return (
        <div className="relative">
            <Header lang={lang} />
            <FirstSection />
            <SecondSection />
            <div className={`${container.home.desktop.section} h-full flex items-center`}>
                <div className="w-full flex flex-col gap-20 py-[100px]">
                    <div className="flex flex-col gap-2">
                        <div className="text-[#10b94e] font-[600] text-[32px]">이용방법</div>
                        <div>
                            <div className="text-[48px] font-[700] leading-[1.4]">간단한 게시물 등록</div>
                            <div className="text-[48px] font-[700] leading-[1.4]">자유로운 소통</div>
                        </div>
                    </div>
                    <div className="flex gap-10">
                        <div className="flex-1 h-[144px] flex gap-2 flex-col justify-center items-center text-[24px] font-[700] shadow-xl rounded-xl bg-white">
                            <MdAppRegistration />
                            <div>산책인 등록</div>
                        </div>
                        <div className="flex-1 h-[144px] flex gap-2 flex-col justify-center items-center text-[24px] font-[700] shadow-xl rounded-xl bg-white">
                            <MdPhoneInTalk />
                            <div>소통 방식 및 시간 설정</div>
                        </div>
                        <div className="flex-1 h-[144px] flex gap-2 flex-col justify-center items-center text-[24px] font-[700] shadow-xl rounded-xl bg-white">
                            <FaWpforms />
                            <div>자유로운 형식</div>
                        </div>
                    </div>
                </div>
            </div>
            <div className={`${container.home.desktop.section} w-full flex justify-center flex-col`}></div>
            <Footer lang={lang} />
        </div>
    )
}
