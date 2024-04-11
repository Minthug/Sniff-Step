import React from 'react'
import Button from '../components/Button'

interface Props {
    lang: string
}

export function Mobile({ lang }: Props) {
    return (
        <footer className="xl:hidden h-full min-h-[203px] flex flex-col justify-between px-4 py-8">
            <div className="flex justify-between">
                <div className="w-full flex flex-col">
                    <img className="w-[96px]" src="/text-logo-1.png" alt="" />
                    <div className="flex flex-wrap text-[12px] gap-8 gap-y-0">
                        <Button className="text-[13px]" contents="홈" href={`/${lang}`} />
                        <Button className="text-[13px]" contents="산책인 등록" href={`/${lang}/register-walker`} />
                        <Button className="text-[13px]" contents="산책 맡기기" href={`/${lang}/boards`} />
                        <Button className="text-[13px]" contents="로그인" href={`/${lang}/signin`} />
                    </div>
                </div>
            </div>
            <div className="flex flex-wrap gap-4 justify-between text-[12px] text-gray-400">
                <div className="flex items-center gap-4">
                    <div>@2024 Sniff & Step</div>
                    <div>Terms</div>
                    <div>Privacy</div>
                </div>
                <div className="flex items-center gap-4">
                    <div>@Minthug</div>
                    <div>@VVSOGI</div>
                </div>
            </div>
        </footer>
    )
}
