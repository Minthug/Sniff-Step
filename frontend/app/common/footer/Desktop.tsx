import React from 'react'
import Button from '../components/Button'

interface Props {
    lang: string
}

export function Desktop({ lang }: Props) {
    return (
        <footer className="xl:flex h-[203px] flex-col justify-between px-24 py-8 hidden">
            <div className="flex justify-between items-center">
                <div>
                    <img className="w-[160px]" src="/text-logo-1.png" alt="" />
                </div>
                <div className="flex gap-16">
                    <Button contents="홈" href={`/${lang}`} />
                    <Button contents="산책 시키기" href={`/${lang}/register-walker`} />
                    <Button contents="산책 맡기기" href={`/${lang}/boards`} />
                    <Button contents="로그인" href={`/${lang}/signin`} />
                </div>
            </div>
            <div className="flex justify-between text-[12px] text-gray-400">
                <div className="flex gap-4">
                    <div>@ 2024 Sniff & Step</div>
                    <div>Terms</div>
                    <div>Privacy</div>
                </div>
                <div className="flex gap-4">
                    <div>@Minthug</div>
                    <div>@VVSOGI</div>
                </div>
            </div>
        </footer>
    )
}
