import React from 'react'
import { AiOutlineClose } from 'react-icons/ai'

interface Props {
    descriptionExample: string
    setShowDescriptionModal: (showDescriptionModal: boolean) => void
    handleDescriptionChange: (description: string) => void
}

export default function DescriptionModal({ descriptionExample, setShowDescriptionModal, handleDescriptionChange }: Props) {
    return (
        <>
            <div onClick={() => setShowDescriptionModal(false)} className="fixed top-0 left-0 w-full h-full bg-[#222] opacity-[60%]" />
            <div className="fixed top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 max-w-[450px] w-full px-4">
                <button
                    onClick={() => setShowDescriptionModal(false)}
                    className="absolute top-2 right-6 w-[40px] h-[40px] flex justify-center items-center"
                >
                    <AiOutlineClose className="text-[#000] text-[24px]" />
                </button>
                <div className="h-[500px] flex px-8 py-8 rounded-t-sm overflow-y-scroll bg-white whitespace-pre-wrap">
                    {descriptionExample}
                </div>
                <button
                    className={`
                        hover:bg-gray-100
                        active:bg-slate-200
                        w-full h-[72px] border-t bg-white rounded-b-sm
                        `}
                    onClick={() => {
                        handleDescriptionChange(descriptionExample)
                        setShowDescriptionModal(false)
                    }}
                >
                    템플릿 적용하기
                </button>
            </div>
        </>
    )
}
