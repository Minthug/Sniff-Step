import { container } from '@/app/common'
import { D2CodingBold } from '@/app/fonts'
import React, { useEffect, useState } from 'react'

export function FirstSection() {
    const [loading, setLoading] = useState(true)

    function opacityChange(element: HTMLElement | null, opacity: string, time: number) {
        setTimeout(() => {
            if (element) {
                element.style.opacity = opacity
            }
        }, time)
    }

    useEffect(() => {
        setLoading(false)
        opacityChange(document.getElementById('poppy-foot1'), '0.4', 400)
        opacityChange(document.getElementById('poppy-foot2'), '0.5', 600)
        opacityChange(document.getElementById('poppy-foot3'), '0.7', 800)
        opacityChange(document.getElementById('poppy-foot4'), '0.8', 1000)
        opacityChange(document.getElementById('poppy-foot5'), '0.9', 1200)
        opacityChange(document.getElementById('poppy-foot6'), '1', 1400)
    }, [])

    return (
        <div className={`${container.home.desktop.section} relative h-[calc(100vh-93px)] flex gap-[100px] select-none`}>
            <div className="relative flex-1">
                <div>
                    <img
                        id="poppy-foot1"
                        className={`
                        2xl:w-[50px] 2xl:bottom-[28%]
                        absolute bottom-[22%] right-[5%] -rotate-[73deg] w-[30px] object-contain opacity-[0] duration-500`}
                        src="/images/poppy-foot.png"
                        alt=""
                    />
                    <img
                        id="poppy-foot2"
                        className={`
                        2xl:w-[50px] 2xl:bottom-[23%]
                        absolute bottom-[18%] right-[20%] -rotate-[105deg] w-[30px] object-contain opacity-[0] duration-500`}
                        src="/images/poppy-foot.png"
                        alt=""
                    />
                    <img
                        id="poppy-foot3"
                        className={`
                        2xl:w-[50px] 2xl:bottom-[28%]
                        absolute bottom-[22%] right-[40%] -rotate-[73deg] w-[30px] object-contain opacity-[0] duration-500`}
                        src="/images/poppy-foot.png"
                        alt=""
                    />
                    <img
                        id="poppy-foot4"
                        className={`
                        2xl:w-[50px] 2xl:bottom-[23%]
                        absolute bottom-[18%] right-[55%] -rotate-[105deg] w-[30px] object-contain opacity-[0] duration-500`}
                        src="/images/poppy-foot.png"
                        alt=""
                    />
                    <img
                        id="poppy-foot5"
                        className={`
                        2xl:w-[50px] 2xl:bottom-[28%]
                        absolute bottom-[22%] left-[20%] -rotate-[70deg] w-[30px] object-contain opacity-[0] duration-500`}
                        src="/images/poppy-foot.png"
                        alt=""
                    />
                    <img
                        id="poppy-foot6"
                        className={`
                        2xl:w-[50px] 2xl:bottom-[23%]
                        absolute bottom-[18%] left-[5%] -rotate-[102deg] w-[30px] object-contain opacity-[0] duration-500`}
                        src="/images/poppy-foot.png"
                        alt=""
                    />
                </div>
                <div
                    style={{
                        opacity: loading ? 0 : 1,
                        transform: loading ? 'translateY(20px)' : 'translateY(0)'
                    }}
                    className={`
                    2xl:text-[52px]
                    h-full flex justify-center flex-col gap-2 pb-[150px] text-[40px] font-[800] z-10 transition-all duration-500 whitespace-pre`}
                >
                    <div>반려견을 위한</div>
                    <div>우리 동네 산책 커뮤니티</div>
                    <button
                        className={`
                                ${D2CodingBold.className}
                                hover:bg-green-600 hover:text-gray-100
                                active:bg-green-700
                                2xl:text-[20px]
                                py-4 mt-4 bg-[#10b94e] rounded-lg font-[500] text-[18px] text-white
                        `}
                    >
                        산책인 공고 구경하기
                    </button>
                </div>
            </div>
            <div className="flex-1 flex justify-center items-center">
                <img className="object-contain" src="/images/main/1-section.png" alt="1-section" />
            </div>
        </div>
    )
}
